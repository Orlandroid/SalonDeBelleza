package com.example.citassalon.presentacion.features.info.productos.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.extensions.GenericResultState
import com.example.citassalon.presentacion.features.extensions.fromJson
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.features.info.stores.StoresAdapter
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.DUMMY_JSON
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.FAKE_STORE
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.domain.state.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfCategoriesFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ClickOnItem<String> {

    private val viewModel: ListOfCategoriesViewModel by viewModels()
    private val adapter = ListOfCategoriesAdapter(this)
    private val args: ListOfCategoriesFragmentArgs by navArgs()
    private var currentStore: StoresAdapter.Store? = null

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.categorias)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpStore()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CategoriesScreen(viewModel)
            }
        }
    }

    @Composable
    fun CategoriesScreen(viewmodel: ListOfCategoriesViewModel) {
        val categories = viewmodel.categories.observeAsState()
        val categoriesDummyjson = viewmodel.categoriesResponse.observeAsState()
        Column(
            Modifier
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
            currentStore?.let { store ->
                when (store.name) {
                    FAKE_STORE -> {
                        ShowCategories(categories = categories)
                    }

                    DUMMY_JSON -> {
                        ShowCategories(categories = categoriesDummyjson)
                    }
                }
            }

        }
    }

    @Composable
    private fun ShowCategories(categories: State<ApiState<List<String>>?>) {
        GenericResultState(state = categories) {
            LazyColumn {
                categories.value?.data?.forEach { category ->
                    item {
                        TextWithArrow(TextWithArrowConfig(text = category, clickOnItem = {
                            clickOnItem(category)
                        }))
                    }
                }
            }
        }
    }


    override fun setUpUi() {
        
    }

    private fun setUpStore() {
        currentStore = args.store.fromJson()
        currentStore?.let { store ->
            when (store.name) {
                FAKE_STORE -> {
                    viewModel.getCategoriesFakeStore()
                }

                DUMMY_JSON -> {
                    viewModel.getCategoriesDummyJson()
                }
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(liveData = viewModel.categories, onLoading = {}, onFinishLoading = {
            //binding.shimmerCategorias.gone()
        }, shouldCloseTheViewOnApiError = true
        ) {
            adapter.setData(it)
            //binding.shimmerCategorias.gone()
        }
        observeApiResultGeneric(
            viewModel.categoriesResponse, shouldCloseTheViewOnApiError = true
        ) {
            //binding.shimmerCategorias.gone()
            adapter.setData(it)
        }
    }


    override fun clickOnItem(element: String, position: Int?) {
        navigate(
            ListOfCategoriesFragmentDirections.actionListOfProductsFragmentToProductsFragment(
                element
            )
        )
    }


}