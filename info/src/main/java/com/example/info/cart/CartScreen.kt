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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.info.R
import com.example.core.navigation.info.InfoNavigationScreens
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.base.BaseScreenState
import com.example.core.ui.base.getContentOrNull
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.dialogs.AlertDialogMessagesConfig
import com.example.core.ui.dialogs.BaseAlertDialogMessages
import com.example.core.ui.dialogs.IsTwoButtonsAlert
import com.example.core.ui.dialogs.ProgressDialog
import com.example.core.ui.theme.AlwaysWhite
import com.example.core.ui.theme.Background
import com.example.core.util.toCurrencyString
import com.example.domain.entities.remote.products.Product
import com.example.domain.wallet.Currency
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
                        InfoNavigationScreens.DetailProductRoute(
                            productId = it.product.id,
                            source = it.source
                        )
                    )
                }
            }
        }
    }
    when {
        uiState.isLoading -> {
            ProgressDialog()
        }

        uiState.error != null -> {
            BaseErrorScreen()
        }

        else -> {
            BaseComposeScreen(
                navController = navController,
                toolbarConfiguration = ToolbarConfiguration(
                    title = uiState.userMoney.toCurrencyString(Currency.USD),
                    showDeleteIcon = true,
                    clickOnDeleteIcon = {
                        viewModel.onEvents(CartEvents.OnDeleteIconClicked)
                    }
                )
            ) {
                if (uiState.showDeleteDialog) {
                    DialogDeleteAllProducts(
                        onEvents = viewModel::onEvents
                    )
                }

                CartScreenContent(
                    products = uiState.products,
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
            isTwoButtonsAlert = IsTwoButtonsAlert(
                clickOnAccept = {
                    onEvents(CartEvents.OnAccept)
                },
                clickOnCancel = {
                    onEvents(CartEvents.OnCancelPressed)
                }
            )
        ),
        onDismissRequest = { onEvents(CartEvents.OnCancelPressed) }
    )
}

@Composable
private fun CartScreenContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
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
                items = products,
                key = { it.id }
            ) { product ->
                ItemCart(
                    product = product,
                    onEvents = onEvents
                )
            }
        }

        // FAKE: el total real debería venir del state/ViewModel, aquí solo sumo precios base
        val total = products.sumOf { it.price }

        OrderSummarySection(
            total = total,
            onPayClicked = {
                onEvents.invoke(CartEvents.OnPay)
            }
        )
    }
}

@Composable
private fun ItemCart(
    product: Product,
    onEvents: (event: CartEvents) -> Unit
) {
    // FAKE: cantidad solo visual, no persiste ni afecta el precio real todavía
    var quantity by remember { mutableIntStateOf(1) }

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
                loading = { CircularProgressIndicator(Modifier.padding(16.dp)) }
            )

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
                    text = product.price.toCurrencyString(Currency.USD),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                // FAKE: stepper visual, no dispara eventos todavía
                QuantityStepper(
                    quantity = quantity,
                    onIncrease = { quantity++ },
                    onDecrease = { if (quantity > 1) quantity-- }
                )
            }

            IconButton(
                onClick = {
                    onEvents(CartEvents.OnRemoveProductClicked)
                }
            ) {
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
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onDecrease,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Disminuir"
            )
        }
        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        IconButton(
            onClick = onIncrease,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Aumentar"
            )
        }
    }
}

@Composable
private fun OrderSummarySection(
    total: Long,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = total.toCurrencyString(Currency.USD),
                    style = MaterialTheme.typography.titleLarge
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
                Text(text = "Pagar")
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
        products = listOf(
            product,
            product.copy(id = 2, title = "Mouse"),
            product.copy(id = 3, title = "Keyboard"),
            product.copy(id = 4, title = "Monitor"),
            product.copy(id = 5, title = "Laptop")
        ),
        onEvents = {}
    )
}