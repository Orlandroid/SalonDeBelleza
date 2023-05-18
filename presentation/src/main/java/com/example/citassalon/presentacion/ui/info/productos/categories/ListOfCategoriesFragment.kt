package com.example.citassalon.presentacion.ui.info.productos.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentListOfCategoriesBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = getString(R.string.categorias)
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        viewModel.getCategories()
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.categories,
            onLoading = {},
            onFinishLoading = { binding.shimmerCategorias.gone() },
            shouldCloseTheViewOnApiError = true
        ) {
            binding.recyclerViewCategorias.adapter = adapter
            adapter.setData(it)
        }
    }


    override fun clikOnElement(element: String, position: Int?) {
        val action =
            ListOfCategoriesFragmentDirections.actionListOfProductsFragmentToProductsFragment(
                element
            )
        navigate(action)
    }


}