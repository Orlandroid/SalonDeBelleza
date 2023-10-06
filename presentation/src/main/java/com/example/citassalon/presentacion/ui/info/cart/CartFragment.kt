package com.example.citassalon.presentacion.ui.info.cart

import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentCartBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.WARNING_MESSAGE
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.click
import com.example.citassalon.presentacion.ui.extensions.getUserMoneyFormat
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.visible
import com.example.citassalon.presentacion.ui.info.productos.productos.ProductsViewModel
import com.example.data.preferences.LoginPeferences
import com.example.domain.entities.remote.Product
import com.example.domain.mappers.toProduct
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart),
    ClickOnItem<Product> {

    @Inject
    lateinit var loginPreferences: LoginPeferences

    private val viewModel: CartViewModel by viewModels()
    private val productsViewModel: ProductsViewModel by viewModels()
    private val cartAdapter = CartAdapter()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getUserMoneyFormat(loginPreferences.getUserMoney())
    )


    override fun setUpUi() {
        with(binding) {
            progressBar.visible()
            /*
            toolbarLayout.toolbarTitle.text = getUserMoneyFormat(loginPreferences.getUserMoney())
            //toolbarLayout.delete.visible()
            toolbarLayout.delete.click {
                showDialogDeleteAllProducts {
                    progressBar.visible()
                    productsViewModel.deleteAllProducts()
                }
            }*/
            buttonPagar.click {

            }
            recyclerCart.adapter = cartAdapter
        }
    }

    private fun showDialogDeleteAllProducts(deleteAllProducts: () -> Unit) {
        val alert = AlertDialogs(kindOfMessage = WARNING_MESSAGE,
            messageBody = getString(R.string.delete_all_products),
            isTwoButtonDialog = true,
            clickOnAccept = object : AlertDialogs.ClickOnAccept {
                override fun clickOnAccept() {
                    deleteAllProducts()
                }

                override fun clickOnCancel() {

                }
            })
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
            binding.progressBar.gone()
        }
    }

    private fun getTotalPriceByProducts(products: List<Product>): String {
        var total = 0.0
        products.forEach {
            total += it.price
        }
        return "$ $total"
    }


    override fun clickOnItem(element: Product, position: Int?) {

    }

}