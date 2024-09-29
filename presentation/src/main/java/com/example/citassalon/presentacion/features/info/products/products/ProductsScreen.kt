package com.example.citassalon.presentacion.features.info.products.products

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ButtonWithIcon
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.Product

@Composable
fun ProductsScreen(
    navController: NavController,
    category: String,
    productsViewModel: ProductsViewModel = hiltViewModel()
) {
    val products = productsViewModel.products.observeAsState()
    LaunchedEffect(Unit) {
        productsViewModel.getProducts(category)
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.productos))
    ) {
        ProductsScreenContent(
            products = products.value?.data,
            goToDetailProduct = { product ->
                navController.navigate(InfoNavigationScreens.DetailProductRoute(product))
            },
            goToCartScreen = {
                navController.navigate(InfoNavigationScreens.CartRoute)
            }
        )
    }

}

@Composable
fun ProductsScreenContent(
    modifier: Modifier = Modifier,
    products: List<Product>?,
    goToCartScreen: () -> Unit,
    goToDetailProduct: (Product) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Image(
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp)
                    .size(50.dp)
                    .clickable {
                        goToCartScreen.invoke()
                    },
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = "ImageCart"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ShowProducts(products = products, goToDetailProduct = goToDetailProduct)

    }
}

@Composable
fun ShowProducts(products: List<Product>?, goToDetailProduct: (Product) -> Unit) {
    products?.let { myProducts ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            myProducts.forEach { product ->
                item {
                    ItemProduct(product = product) {
                        goToDetailProduct.invoke(product)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemProduct(
    modifier: Modifier = Modifier,
    product: Product,
    goToDetailProduct: (Product) -> Unit
) {
    val imageLoader =
        LocalContext.current.imageLoader.newBuilder().logger(DebugLogger()).build()
    var bitmapImageProduct: Bitmap? = null
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(4.dp),
        onClick = {
            goToDetailProduct(product)
        },
        colors = CardDefaults.cardColors(containerColor = AlwaysWhite)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ButtonWithIcon(
            modifier = modifier.padding(horizontal = 8.dp),
            imageVector = ImageVector.vectorResource(
                id = R.drawable.ic_baseline_add_24
            ),
            buttonText = stringResource(R.string.agregar),
            backgroundColor = Color.White,
            onClick = {

            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            model = product.image,
            contentDescription = "ImageProduct",
            imageLoader = imageLoader,
            onSuccess = {
                bitmapImageProduct = it.result.drawable.toBitmapOrNull()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = product.title, Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = product.price.toString(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun ProductsScreenContentPreview() {
    ProductsScreenContent(
        products = emptyList(),
        goToCartScreen = {},
        goToDetailProduct = {}
    )
}


