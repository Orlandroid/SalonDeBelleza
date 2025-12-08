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
import androidx.compose.runtime.getValue
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
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.BaseScreenStateV2
import com.example.citassalon.presentacion.features.base.ErrorState
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.base.getContentOrNull
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.theme.Background
import kotlinx.coroutines.flow.collectLatest

const val FAKE_STORE = "Fake store"
const val DUMMY_JSON = "DummyJSON"


data class Store(
    val name: String = ""
)

@Composable
fun CategoriesScreen(
    navController: NavController,
    viewmodel: ListOfCategoriesViewModel = hiltViewModel()
) {
    val uiState by viewmodel.state.collectAsStateWithLifecycle()
    when (uiState) {
        BaseScreenStateV2.OnLoading -> {
            ProgressDialog()
        }

        is BaseScreenStateV2.OnContent<*> -> {
            LaunchedEffect(Unit) {
                viewmodel.effects.collectLatest {
                    when (it) {
                        is CategoriesEffects.NavigateToProducts -> {
                            navController.navigate(InfoNavigationScreens.ProductsRoute(category = it.category))
                        }
                    }
                }
            }
            BaseComposeScreen(
                navController = navController,
                toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.categorias))
            ) {
                uiState.getContentOrNull()?.let { categoriesUiState ->
                    CategoriesScreenContent(
                        categories = categoriesUiState.categories,
                        store = Store(FAKE_STORE),
                        onEvent = viewmodel::onEvents
                    )
                }
            }
        }

        is BaseScreenStateV2.OnError -> {
            ErrorState(AlertDialogMessagesConfig(bodyMessage = "Error${(uiState as BaseScreenStateV2.OnError).error}"))
        }
    }
}

@Composable
private fun CategoriesScreenContent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    store: Store,
    onEvent: (event: CategoriesEvents) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediumSpacer(orientation = Orientation.VERTICAL)
        Image(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp),
            painter = painterResource(id = R.drawable.estar),
            contentDescription = null
        )
        MediumSpacer(orientation = Orientation.VERTICAL)
        store.let { store ->
            when (store.name) {
                FAKE_STORE -> {
                    Categories(categories = categories) { category ->
                        onEvent(CategoriesEvents.OnCategoryClicked(category))
                    }
                }

                DUMMY_JSON -> {
                    //Todo Add the dummyProduct json api
//                  ShowCategories(categories = categoriesDummyjson)
                }
            }
        }

    }
}

@Composable
private fun Categories(
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
private fun CategoriesScreenContentPreview() {
    CategoriesScreenContent(
        categories = listOf(
            "electronics",
            "jewelery",
            "men,s clothing"
        ),
        store = Store(FAKE_STORE),
        onEvent = {}
    )
}














