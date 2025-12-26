package com.example.citassalon.presentacion.features.info.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.getContentOrNull
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.ProductUi

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = "8.0 $")
    ) {
        uiState.value.getContentOrNull()?.let { state ->
            CartScreenContent(products = state.products)
        }
    }

}

@Composable
private fun CartScreenContent(
    modifier: Modifier = Modifier,
    products: List<ProductUi>?
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        products?.let { listProducts ->
            if (listProducts.isNotEmpty()) {
                listProducts.forEach { product ->
                    item {
                        ItemCart(productDb = product)
                    }
                }
            }
        }
    }

}

@Composable
private fun ItemCart(productDb: ProductUi) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AlwaysWhite
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(100.dp),
                painter = painterResource(R.drawable.image15_mask),
                contentDescription = "Image of product"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = productDb.title
            )
            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                text = "$ ${productDb.price}",
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun CartScreenContentPreview() {
    val product = ProductUi(
        id = 1,
        title = "Usb",
        price = 45.0,
        description = "WD 2TB Elements Portable External Hard Drive - USB 3.0",
        category = "",
        image = "",
        imageBase64 = "",
        rating = 1.0
    )
    CartScreenContent(
        products = listOf(
            product,
            product,
            product,
            product,
            product
        )
    )
}


