package com.example.citassalon.presentacion.features.info.products.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.base.getContentOrNull
import com.example.citassalon.presentacion.features.components.BaseErrorScreen
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.theme.Background
import com.example.data.remote.products.commons.category.CategorySource
import kotlinx.coroutines.flow.collectLatest

const val FAKE_STORE = "Fake store"
const val DUMMY_JSON = "DummyJSON"
const val PLATZY = "Platzy"
const val MyDummy = "MyDummy"


data class Store(
    val name: String = ""
)

@Composable
fun CategoriesScreen(
    navController: NavController,
    categorySource: CategorySource,
    viewmodel: CategoriesViewModel = hiltViewModel(
        creationCallback = { factory: CategoriesViewModelFactory -> factory.create(categorySource) })
) {
    val uiState = viewmodel.state.collectAsStateWithLifecycle()
    when (uiState.value) {
        BaseScreenState.OnLoading -> {
            ProgressDialog()
        }

        is BaseScreenState.OnContent -> {
            LaunchedEffect(Unit) {
                viewmodel.effects.collectLatest {
                    when (it) {
                        is CategoriesEffects.NavigateToProducts -> {
                            navController.navigate(
                                InfoNavigationScreens.ProductsRoute(
                                    source = it.source,
                                    category = it.category
                                )
                            )
                        }
                    }
                }
            }
            BaseComposeScreen(
                navController = navController,
                toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.categorias))
            ) {
                uiState.value.getContentOrNull()?.let { categoriesUiState ->
                    CategoriesScreenContent(
                        categories = categoriesUiState.categories,
                        onEvent = viewmodel::onEvents
                    )
                }
            }
        }

        is BaseScreenState.OnError -> {
            BaseErrorScreen()
        }
    }
}

@Composable
private fun CategoriesScreenContent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    onEvent: (event: CategoriesEvents) -> Unit
) {
    Column(
        modifier = modifier
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
        Categories(categories = categories) { category ->
            onEvent(CategoriesEvents.OnCategoryClicked(category))
        }
    }
}

@Composable
private fun Categories(
    categories: List<String>,
    goToProductsScreen: (category: String) -> Unit
) {
    LazyColumn {

        items(items = categories, key = { it }) { category ->
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = category,
                    clickOnItem = {
                        goToProductsScreen(category)
                    }
                )
            )
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
        onEvent = {}
    )
}














