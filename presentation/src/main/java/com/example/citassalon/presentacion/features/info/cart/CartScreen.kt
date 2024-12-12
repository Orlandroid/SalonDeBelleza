package com.example.citassalon.presentacion.features.info.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.extensions.base64StringToBitmap
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.Product
import com.example.domain.mappers.toProduct

@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = hiltViewModel()) {
    val allProducts = viewModel.allIProducts.observeAsState()
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = "8.0 $")
    ) {
        CartScreenContent(products = allProducts.value)
    }

}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    products: List<ProductDb>?
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val (list) = createRefs()
        LazyColumn(modifier = Modifier.constrainAs(list) {
        }
        ) {
            products?.let { listProducts ->
                if (listProducts.isNotEmpty()) {
                    listProducts.forEach { product ->
                        item {
                            ItemCart(productDb = product.toProduct())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCart(productDb: Product) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AlwaysWhite
        )
    ) {
        ConstraintLayout {
            val (image, name, price) = createRefs()
            Image(
                modifier = Modifier.constrainAs(image) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                },
                bitmap = productDb.image.base64StringToBitmap().asImageBitmap(),
                contentDescription = "Image of product"
            )
            Text(
                text = productDb.title,
                modifier = Modifier
                    .constrainAs(name) {
                        start.linkTo(image.end)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.value(120.dp)
                        height = Dimension.wrapContent
                    }
            )
            Text(text = "$ ${productDb.price}",
                modifier = Modifier.constrainAs(price) {
                    linkTo(parent.top, parent.bottom)
                    start.linkTo(name.end)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CartScreenContentPreview() {
    ItemCart(Product.dummyProduct())
}


