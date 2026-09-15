package com.example.info.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.core.navigation.info.InfoNavigationScreens.DetailProductRoute
import com.example.core.navigation.info.InfoNavigationScreens.SuccessScreenRoute
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.dialogs.AlertDialogMessagesConfig
import com.example.core.ui.dialogs.BaseAlertDialogMessages
import com.example.core.ui.dialogs.IsTwoButtonsAlert
import com.example.core.ui.dialogs.ProgressDialog
import com.example.core.ui.theme.AlwaysWhite
import com.example.core.ui.theme.Background
import com.example.core.util.toCurrencyString
import com.example.domain.Product
import com.example.domain.loyalty.PromotionCode
import com.example.domain.wallet.Currency
import com.example.info.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest {
            when (it) {
                is CartEffects.OnProductsDeleted -> {
                    snackBarHostState.showSnackbar(it.message)
                }

                is CartEffects.NavigateToProductDetail -> {
                    navController.navigate(
                        DetailProductRoute(
                            productId = it.product.id, source = it.source
                        )
                    )
                }

                CartEffects.OnPurchaseCompleted -> {
                    navController.navigate(SuccessScreenRoute)
                }
            }
        }
    }

    when {
        uiState.isLoading -> {
            ProgressDialog()
        }

        uiState.error != null -> {
            BaseErrorScreen(
                title = uiState.error.orEmpty(),
                message = stringResource(R.string.purchase_error)
            )
        }

        else -> {
            BaseComposeScreen(
                navController = navController,
                toolbarConfiguration = ToolbarConfiguration(
                    title = uiState.userMoney.toCurrencyString(Currency.USD),
                    showDeleteIcon = true,
                    clickOnDeleteIcon = {
                        viewModel.onEvents(CartEvents.OnDeleteIconClicked)
                    })
            ) {
                if (uiState.showDeleteDialog) {
                    DialogDeleteAllProducts(
                        onEvents = viewModel::onEvents
                    )
                }

                CartScreenContent(
                    uiState = uiState,
                    onEvents = viewModel::onEvents
                )
            }
        }
    }
}

@Composable
private fun DialogDeleteAllProducts(onEvents: (event: CartEvents) -> Unit) {
    BaseAlertDialogMessages(
        alertDialogMessagesConfig = AlertDialogMessagesConfig(
            bodyMessage = stringResource(R.string.delete_all_products_sure),
            isTwoButtonsAlert = IsTwoButtonsAlert(clickOnAccept = {
                onEvents(CartEvents.OnAccept)
            }, clickOnCancel = {
                onEvents(CartEvents.OnCancelPressed)
            })
        ), onDismissRequest = { onEvents(CartEvents.OnCancelPressed) })
}

@Composable
private fun CartScreenContent(
    modifier: Modifier = Modifier,
    uiState: CartUiState,
    onEvents: (event: CartEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(
                items = uiState.products, key = { it.id }) { product ->
                ProductItem(
                    product = product, onEvents = onEvents
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                PromoCodeSection(
                    promoCode = uiState.promoCode,
                    isApplied = uiState.isPromoApplied,
                    error = uiState.promoError,
                    isLoading = uiState.validatingPromo,
                    onCodeChange = { onEvents(CartEvents.OnPromoCodeChanged(it)) },
                    onApply = { onEvents(CartEvents.OnApplyPromoCode) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        OrderSummarySection(
            total = uiState.cartTotal,
            appliedPromo = uiState.appliedPromo,
            isLoading = uiState.showLoadingButton,
            onPayClicked = { onEvents.invoke(CartEvents.OnPay) }
        )
    }
}

@Composable
private fun PromoCodeSection(
    promoCode: String,
    isApplied: Boolean,
    error: String?,
    isLoading: Boolean,
    onCodeChange: (String) -> Unit,
    onApply: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AlwaysWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.discount_coupon),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = promoCode,
                    onValueChange = onCodeChange,
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.enter_your_code),
                            fontSize = 14.sp
                        )
                    },
                    singleLine = true,
                    enabled = !isApplied && !isLoading,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onApply,
                    enabled = !isApplied && promoCode.isNotBlank() && !isLoading,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = AlwaysWhite,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(
                                if (isApplied) R.string.coupon_applied
                                else R.string.apply_coupon
                            )
                        )
                    }
                }
            }
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (isApplied) {
                Text(
                    text = stringResource(R.string.coupon_applied_successfully),
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: Product, onEvents: (event: CartEvents) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AlwaysWhite
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.size(80.dp),
                model = product.image,
                contentDescription = "ImageProduct",
                loading = { CircularProgressIndicator(Modifier.padding(16.dp)) })

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.total().toCurrencyString(Currency.USD),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                QuantityStepper(quantity = product.quantity, onIncrease = {
                    onEvents(CartEvents.OnIncrease(productId = product.id))
                }, onDecrease = {
                    onEvents(CartEvents.OnDecrease(productId = product.id))
                })
            }

            IconButton(
                onClick = {
                    onEvents(CartEvents.OnRemoveProductClicked(productId = product.id))
                }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.remove)
                )
            }
        }
    }
}

@Composable
private fun QuantityStepper(
    quantity: Int, onIncrease: () -> Unit, onDecrease: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onDecrease, modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove, contentDescription = "Decrease"
            )
        }
        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        IconButton(
            onClick = onIncrease, modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Increase"
            )
        }
    }
}

@Composable
private fun OrderSummarySection(
    total: Long,
    appliedPromo: PromotionCode?,
    isLoading: Boolean,
    onPayClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AlwaysWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (appliedPromo != null) {
                val discount = total.toDouble() * appliedPromo.discountPercentage / 100.0
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        total.toCurrencyString(Currency.USD),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Descuento (${appliedPromo.discountPercentage}%)",
                        color = Color(0xFF4CAF50),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "-${discount.toCurrencyString(Currency.USD)}",
                        color = Color(0xFF4CAF50),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total", style = MaterialTheme.typography.titleMedium
                )
                val finalTotal = if (appliedPromo != null) {
                    total.toDouble() - (total.toDouble() * appliedPromo.discountPercentage / 100.0)
                } else {
                    total.toDouble()
                }
                Text(
                    text = finalTotal.toCurrencyString(Currency.USD),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = onPayClicked,
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp), color = AlwaysWhite
                    )
                } else {
                    Text(text = stringResource(R.string.pay))
                }
            }

        }
    }
}


@Composable
@Preview(showBackground = true)
private fun CartScreenContentPreview() {
    val product = Product(
        id = 1,
        title = "Usb",
        price = 45L,
        description = "WD 2TB Elements Portable External Hard Drive - USB 3.0",
        rating = 1.0,
        image = "",
    )
    CartScreenContent(
        uiState = CartUiState(
            products = listOf(
                product,
                product.copy(id = 2, title = "Mouse"),
                product.copy(id = 3, title = "Keyboard"),
            ),
            cartTotal = 458L
        ),
        onEvents = {}
    )
}
