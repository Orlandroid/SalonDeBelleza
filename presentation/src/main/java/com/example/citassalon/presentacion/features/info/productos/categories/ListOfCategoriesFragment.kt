package com.example.citassalon.presentacion.features.info.productos.categories

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentListOfCategoriesBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.fromJson
import com.example.citassalon.presentacion.features.extensions.gone
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.features.info.stores.StoresAdapter
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.DUMMY_JSON
import com.example.citassalon.presentacion.features.info.stores.StoresFragment.Companion.FAKE_STORE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfCategoriesFragment :
    BaseFragment<FragmentListOfCategoriesBinding>(R.layout.fragment_list_of_categories),
    ClickOnItem<String> {

    private val viewModel: ListOfCategoriesViewModel by viewModels()
    private val adapter = ListOfCategoriesAdapter(this)
    private val args: ListOfCategoriesFragmentArgs by navArgs()
    private var currentStore: StoresAdapter.Store? = null

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.categorias)
    )

    override fun setUpUi() {
        binding.recyclerViewCategorias.adapter = adapter
        setUpStore()
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
        observeApiResultGeneric(
            liveData = viewModel.categories,
            onLoading = {},
            onFinishLoading = { binding.shimmerCategorias.gone() },
            shouldCloseTheViewOnApiError = true
        ) {
            adapter.setData(it)
            binding.shimmerCategorias.gone()
        }
        observeApiResultGeneric(
            viewModel.categoriesResponse, shouldCloseTheViewOnApiError = true
        ) {
            binding.shimmerCategorias.gone()
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