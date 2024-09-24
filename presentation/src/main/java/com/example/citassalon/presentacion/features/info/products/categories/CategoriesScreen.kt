package com.example.citassalon.presentacion.features.info.products.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.info.stores.StoresFragment
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.DUMMY_JSON
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.FAKE_STORE
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun CategoriesScreen(
    navController: NavController,
    viewmodel: ListOfCategoriesViewModel = hiltViewModel()
) {
    val categories = viewmodel.categories.observeAsState()
    val categoriesDummyjson =
        viewmodel.categoriesResponse.observeAsState()//we need to add one parameters from navigation
    LaunchedEffect(true) {
        viewmodel.getCategoriesFakeStore()
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.categorias))
    ) {
        CategoriesScreenContent(
            categories = categories.value?.data ?: emptyList(),
            store = StoresFragment.Store(FAKE_STORE)
        ) {
            navController.navigate(InfoNavigationScreens.Products.route)
        }
    }
}

@Composable
fun CategoriesScreenContent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    store: StoresFragment.Store,
    goToProductsScreen: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediumSpacer(orientation = Orientation.VERTICAL)
        Image(
            painter = painterResource(id = R.drawable.estar),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
        )
        MediumSpacer(orientation = Orientation.VERTICAL)
        store.let { store ->
            when (store.name) {
                FAKE_STORE -> {
                    ShowCategories(categories = categories) {
                        goToProductsScreen()
                    }
                }

                DUMMY_JSON -> {
//                    ShowCategories(categories = categoriesDummyjson)
                }
            }
        }

    }
}

@Composable
private fun ShowCategories(categories: List<String>, goToProductsScreen: () -> Unit) {
    LazyColumn {
        categories.forEach { category ->
            item {
                TextWithArrow(
                    TextWithArrowConfig(
                        text = category,
                        clickOnItem = {
                            goToProductsScreen.invoke()
                        }
                    )
                )
            }
        }
    }

}


@Composable
@Preview(showBackground = true)
fun CategoriesScreenContentPreview() {
    CategoriesScreenContent(
        categories = emptyList(),
        store = StoresFragment.Store(""),
        goToProductsScreen = {}
    )
}














