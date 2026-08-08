package com.example.citassalon.presentacion.features.info.stores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.core.info.InfoNavigationScreens
import com.example.domain.ProductSource
import com.example.domain.toCategorySource
import kotlin.random.Random

@Composable
fun StoresScreen(
    navController: NavController
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.stores))
    ) {
        StoresScreenContent { source ->
            if (source.supportsCategories) {
                source.toCategorySource()?.let { categorySource ->
                    navController.navigate(InfoNavigationScreens.CategoriesRoute(source = categorySource))
                }
            } else {
                navController.navigate(InfoNavigationScreens.ProductsRoute(source = source))
            }
        }
    }
}

@Composable
private fun StoresScreenContent(
    modifier: Modifier = Modifier,
    goToCategories: (source: ProductSource) -> Unit
) {
    Column(
        modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(setAnimation()))
        Spacer(Modifier.height(32.dp))
        LottieAnimation(
            modifier = Modifier
                .height(250.dp)
                .width(250.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        StoresMenu(goToStoresList = goToCategories)
    }
}

@Composable
private fun StoresMenu(
    goToStoresList: (source: ProductSource) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {
        items(stores) { store ->
            Spacer(modifier = Modifier.height(8.dp))
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = store.name,
                    clickOnItem = {
                        goToStoresList(store.source)
                    }
                )
            )
        }
    }
}

private val stores = listOf(
    Store(
        name = FAKE_STORE,
        source = ProductSource.FAKE_STORE
    ),
    Store(
        name = DUMMY_JSON,
        source = ProductSource.DUMMY_JSON
    ),
    Store(
        name = PLATZY,
        source = ProductSource.PLATZI
    ),
    Store(
        name = MyDummy,
        source = ProductSource.MY_DUMMY_API
    )
)

private fun setAnimation(): Int {
    val random = Random.nextInt(1, 2)
    return if (random % 2 == 0) {
        R.raw.ecomerce
    } else {
        R.raw.ecomerce2
    }
}

@Composable
@Preview(showBackground = true)
private fun StoresScreenContentPreview() {
    StoresScreenContent(goToCategories = {})
}