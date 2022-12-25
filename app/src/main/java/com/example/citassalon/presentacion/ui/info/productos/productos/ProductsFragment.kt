package com.example.citassalon.presentacion.ui.info.productos.productos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citassalon.R
import com.example.citassalon.data.mappers.toProductDb
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.databinding.FragmentProductsBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.INFO_MESSAGE
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>(R.layout.fragment_products),
    ClickOnItem<Product> {

    private val viewModel: ProductsViewModel by viewModels()
    private var adapter: ProductsAdapter? = null
    private val args: ProductsFragmentArgs by navArgs()
    private var skeleton: Skeleton? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = getString(R.string.productos)
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            adapter = ProductsAdapter(object : ProductsAdapter.ClickOnItems {
                override fun clickOnElement(product: Product) {
                    val action =
                        ProductsFragmentDirections.actionProductsFragmentToDetalleProductoFragment(
                            product
                        )
                    findNavController().navigate(action)
                }

                override fun clickOnAddToCard(product: Product) {
                    viewModel.insertProduct(product.toProductDb()) {
                        showDialogConfirmationAddProduct()
                    }
                }

            })
            imageCart.setOnClickListener {
                val action = ProductsFragmentDirections.actionProductsFragmentToCartFragment()
                navigate(action)
            }
            skeleton = recyclerProducts.applySkeleton(R.layout.item_product, 8)
            recyclerProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        viewModel.getProducts(args.categoria)
    }


    private fun showDialogConfirmationAddProduct() {
        val dialog = AlertDialogs(
            kindOfMessage = INFO_MESSAGE,
            messageBody = getString(R.string.product_add)
        )
        activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.products,
            onLoading = { skeleton?.showSkeleton() },
            onFinishLoading = { skeleton?.showOriginal() },
            haveTheViewProgress = false
        ) {
            binding.recyclerProducts.adapter = adapter
            adapter?.setData(it)
            binding.root.setBackgroundColor(resources.getColor(R.color.background))
        }
    }


    override fun clikOnElement(element: Product, position: Int?) {
        val action = ProductsFragmentDirections.actionProductsFragmentToDetalleProductoFragment(element)
        navigate(action)
    }


}