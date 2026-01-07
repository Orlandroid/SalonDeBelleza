package com.example.citassalon.presentacion.features.info.products.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.features.base.getContentOrNull
import com.example.citassalon.presentacion.features.components.ButtonWithIcon
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens.DetailProductRoute
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.Product
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductsScreen(
    navController: NavController,
    category: String,
    productsViewModel: ProductsViewModel = hiltViewModel(
        creationCallback = { factory: ProductsViewModelFactory -> factory.create(category) })
) {
    val uiState by productsViewModel.state.collectAsStateWithLifecycle()
    when (uiState) {
        is BaseScreenState.OnContent -> {
            LaunchedEffect(Unit) {
                productsViewModel.effects.collectLatest {
                    when (it) {
                        is ProductScreenEffects.NavigateToCar -> {
                            navController.navigate(InfoNavigationScreens.CartRoute)
                        }

                        is ProductScreenEffects.NavigateToProductDetail -> {
                            navController.navigate(DetailProductRoute(it.product))
                        }

                        ProductScreenEffects.ProductSaved -> {

                        }

                        ProductScreenEffects.NoProductsToDelete -> {}
                        ProductScreenEffects.ProductsDeletedSuccessfully -> {

                        }
                    }
                }
            }
            BaseComposeScreen(
                navController = navController,
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.productos)
                )
            ) {
                uiState.getContentOrNull()?.let { uiState ->
                    ProductsScreenContent(
                        products = uiState.products,
                        onEvents = productsViewModel::onEvents
                    )
                }
            }
        }

        is BaseScreenState.OnError -> {

        }

        BaseScreenState.OnLoading -> {
            ProgressDialog()
        }
    }
}

@Composable
private fun ProductsScreenContent(
    modifier: Modifier = Modifier,
    products: List<Product>?,
    onEvents: (event: ProductScreenEvents) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background)
    ) {
        ContainerImageCart {
            Image(
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp)
                    .size(50.dp)
                    .clickable {
                        onEvents(ProductScreenEvents.OnCarClicked)
                    },
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = "ImageCart"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Products(products = products.orEmpty(), onEvents = onEvents)
    }
}

@Composable
private fun ContainerImageCart(
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        content()
    }
}

@Composable
private fun Products(
    products: List<Product>,
    onEvents: (event: ProductScreenEvents) -> Unit
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.Center,
        columns = GridCells.Fixed(2)
    ) {
        items(products.size) {
            ItemProduct(
                product = products[it],
                onEvents = onEvents
            )
        }
    }

}

@Composable
private fun ItemProduct(
    modifier: Modifier = Modifier,
    product: Product,
    onEvents: (event: ProductScreenEvents) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(4.dp),
        onClick = {
            onEvents(ProductScreenEvents.OnProductClicked(product))
        },
        colors = CardDefaults.cardColors(containerColor = AlwaysWhite)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ButtonAdd(product = product, onEvents = onEvents)
        Spacer(modifier = Modifier.height(16.dp))
        ImageProduct(productImage = product.image)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = product.title
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            text = product.price.toString(),
        )
    }
}

@Composable
private fun ButtonAdd(
    product: Product,
    onEvents: (event: ProductScreenEvents) -> Unit
) {
    ButtonWithIcon(
        modifier = Modifier.padding(horizontal = 8.dp),
        imageVector = ImageVector.vectorResource(
            id = R.drawable.ic_baseline_add_24
        ),
        buttonText = stringResource(R.string.agregar),
        backgroundColor = Color.White,
        onClick = {
            onEvents(ProductScreenEvents.OnAddProduct(product = product))
        }
    )
}

@Composable
private fun ColumnScope.ImageProduct(productImage: String) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .size(100.dp)
            .align(Alignment.CenterHorizontally),
        model = productImage,
        contentDescription = "ImageProduct",
        loading = { CircularProgressIndicator(Modifier.padding(16.dp)) }
    )
}


@Composable
@Preview(showBackground = true)
private fun ProductsScreenContentPreview() {
    ProductsScreenContent(
        products = listOf(
            Product.dummyProduct(),
            Product.dummyProduct(),
            Product.dummyProduct(),
            Product.dummyProduct()
        ),
        onEvents = {}
    )
}


