package com.example.citassalon.presentacion.ui.info.productos.categories

import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentListOfCategoriesBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfCategoriesFragment :
    BaseFragment<FragmentListOfCategoriesBinding>(R.layout.fragment_list_of_categories),
    ClickOnItem<String> {

    private val viewModel: ListOfCategoriesViewModel by viewModels()
    private val adapter = ListOfCategoriesAdapter(this)
    private val categories = arrayListOf<String>()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.categorias)
    )

    override fun setUpUi() {
        binding.recyclerViewCategorias.adapter = adapter
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.categories,
            onLoading = {},
            onFinishLoading = { binding.shimmerCategorias.gone() },
            shouldCloseTheViewOnApiError = true
        ) {
            categories.addAll(it)
            //viewModel.getCategoriesStore()
        }
        observeApiResultGeneric(viewModel.categoriesResponse, shouldCloseTheViewOnApiError = true) {
            categories.addAll(it)
            adapter.setData(categories)
        }
    }


    override fun clikOnElement(element: String, position: Int?) {
        navigate(
            ListOfCategoriesFragmentDirections.actionListOfProductsFragmentToProductsFragment(
                element
            )
        )
    }


}