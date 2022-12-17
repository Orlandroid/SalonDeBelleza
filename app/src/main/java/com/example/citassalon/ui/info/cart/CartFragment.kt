package com.example.citassalon.ui.info.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.mappers.toProduct
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.databinding.FragmentCartBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.main.AlertDialogs.Companion.INFO_MESSAGE
import com.example.citassalon.main.AlertDialogs.Companion.WARNING_MESSAGE
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.*
import com.example.citassalon.ui.info.productos.productos.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart),
    ClickOnItem<Product> {

    private val viewModel: CartViewModel by viewModels()
    private val productsViewModel: ProductsViewModel by viewModels()
    private val cartAdapter = CartAdapter()
    private var total = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            showProgress()
            toolbarLayout.toolbarTitle.text = getString(R.string.carrito)
            toolbarLayout.delete.visible()
            toolbarLayout.delete.click {
                showDialogDeleteAllProducts {
                    productsViewModel.deleteAllProducts()
                }
            }
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            recyclerCart.adapter = cartAdapter
        }
    }

    private fun showDialogDeleteAllProducts(deleteAllProducts: () -> Unit) {
        val alert = AlertDialogs(
            kindOfMessage = WARNING_MESSAGE,
            messageBody = getString(R.string.delete_all_products),
            isTwoButtonDialog = true,
            clickOnAccept = object : AlertDialogs.ClickOnAccept {
                override fun clickOnAccept() {
                    deleteAllProducts()
                }

                override fun clickOnCancel() {

                }
            }
        )
        activity?.let { alert.show(it.supportFragmentManager, "alertMessage") }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.allIProducts.observe(this.viewLifecycleOwner) { items ->
            if (items.isEmpty()) {
                binding.containerInfoPago.gone()
                binding.noData.visible()
                cartAdapter.setData(listOf())
            } else {
                binding.containerInfoPago.visible()
                binding.noData.gone()
                val listProducts = arrayListOf<Product>()
                items.forEach {
                    listProducts.add(it.toProduct())
                }
                binding.tvTotalProducts.text = getTotalPriceByProducts(listProducts)
                cartAdapter.setData(listProducts)
            }
            hideProgress()
        }
    }

    private fun getTotalPriceByProducts(products: List<Product>): String {
        var total = 0.0
        products.forEach {
            total += it.price
        }
        return "$ $total"
    }


    override fun clikOnElement(element: Product, position: Int?) {

    }

}