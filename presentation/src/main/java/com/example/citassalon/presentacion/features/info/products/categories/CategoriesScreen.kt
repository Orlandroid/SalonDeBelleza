package com.example.citassalon.presentacion.features.info.products.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.info.stores.StoresFragment
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.DUMMY_JSON
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.FAKE_STORE
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.state.ApiState

@Composable
fun CategoriesScreen(
    navController: NavController,
    viewmodel: ListOfCategoriesViewModel = hiltViewModel()
) {
    val categories = viewmodel.categories.observeAsState()
    val categoriesDummyjson = viewmodel.categoriesResponse.observeAsState()
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.staff))
    ) {
        CategoriesScreenContent(categories = categories, store = StoresFragment.Store(""))
    }
}

@Composable
fun CategoriesScreenContent(
    modifier: Modifier = Modifier,
    categories: State<ApiState<List<String>>?>,
    store: StoresFragment.Store,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.estar),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
        )
        store.let { store ->
            when (store.name) {
                FAKE_STORE -> {
                    ShowCategories(categories = categories)
                }

                DUMMY_JSON -> {
//                    ShowCategories(categories = categoriesDummyjson)
                }
            }
        }

    }
}

@Composable
private fun ShowCategories(categories: State<ApiState<List<String>>?>) {
    LazyColumn {
        categories.value?.data?.forEach { category ->
            item {
                TextWithArrow(
                    TextWithArrowConfig(
                        text = category,
                        clickOnItem = {

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
//    CategoriesScreenContent()

}














