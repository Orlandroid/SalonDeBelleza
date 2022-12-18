package com.example.citassalon.presentacion.ui.info.productos.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.databinding.FragmentListOfCategoriesBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.ERROR_MESSAGE
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.navigate
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
        viewModel.categories.observe(viewLifecycleOwner) { apiState ->
            when (apiState) {
                is ApiState.Loading -> {

                }
                is ApiState.Success -> {
                    if (apiState.data != null) {
                        binding.recyclerViewCategorias.adapter = adapter
                        adapter.setData(apiState.data)
                    }
                    binding.shimmerCategorias.gone()
                }
                is ApiState.Error -> {
                    val dialog = AlertDialogs(
                        kindOfMessage = ERROR_MESSAGE,
                        messageBody = "Error al obtener datos",
                        clickOnAccept = object : AlertDialogs.ClickOnAccept {
                            override fun clickOnAccept() {
                                findNavController().popBackStack()
                            }

                            override fun clickOnCancel() {

                            }
                        }
                    )
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                    binding.shimmerCategorias.gone()
                }
                is ApiState.ErrorNetwork -> {
                    val dialog = AlertDialogs(
                        kindOfMessage = ERROR_MESSAGE,
                        messageBody = "Verifica tu conexion de internet",
                        clickOnAccept = object : AlertDialogs.ClickOnAccept {
                            override fun clickOnAccept() {
                                findNavController().popBackStack()
                            }

                            override fun clickOnCancel() {

                            }
                        }
                    )
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                    binding.shimmerCategorias.gone()
                    findNavController().popBackStack()
                }
                is ApiState.NoData -> {

                }
            }

        }
    }

    //gives one error somethings
    override fun clikOnElement(element: String, position: Int?) {
        val action =
            ListOfCategoriesFragmentDirections.actionListOfProductsFragmentToProductsFragment(
                element
            )
        navigate(action)
    }


}