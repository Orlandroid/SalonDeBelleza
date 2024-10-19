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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreenState
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
    val categories = viewmodel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (viewmodel.wasCallService.not()) {
            viewmodel.wasCallService = true
            viewmodel.getCategoriesFakeStoreV2()
        }
    }
    BaseComposeScreenState(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.categorias)),
        state = categories.value
    ) { result ->
        CategoriesScreenContent(
            categories = result,
            store = StoresFragment.Store(FAKE_STORE)
        ) { chosenCategory ->
            navController.navigate(InfoNavigationScreens.ProductsRoute(category = chosenCategory))
        }
    }
}

@Composable
fun CategoriesScreenContent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    store: StoresFragment.Store,
    goToProductsScreen: (category: String) -> Unit
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
                    ShowCategories(categories = categories) { category ->
                        goToProductsScreen(category)
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
private fun ShowCategories(
    categories: List<String>,
    goToProductsScreen: (category: String) -> Unit
) {
    LazyColumn {
        categories.forEach { category ->
            item {
                TextWithArrow(
                    TextWithArrowConfig(
                        text = category,
                        clickOnItem = {
                            goToProductsScreen(category)
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
        categories = listOf(
            "electronics",
            "jewelery",
            "men,s clothing"
        ),
        store = StoresFragment.Store("Android"),
        goToProductsScreen = {}
    )
}














