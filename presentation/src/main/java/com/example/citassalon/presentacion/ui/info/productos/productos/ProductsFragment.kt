package com.example.citassalon.presentacion.ui.info.productos.productos

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentProductsBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.ui.extensions.showSuccessMessage
import com.example.citassalon.presentacion.ui.extensions.toJson
import com.example.domain.entities.remote.Product
import com.example.domain.mappers.toProductDb
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
    private var isFirstTimeOnTheView = true

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.productos)
    )


    override fun setUpUi() {
        if (isFirstTimeOnTheView) {
            viewModel.getProducts(args.categoria)
            isFirstTimeOnTheView = false
        }
        with(binding) {
            adapter = ProductsAdapter(object : ProductsAdapter.ClickOnItems {
                override fun clickOnElement(product: Product) {
                    val action =
                        ProductsFragmentDirections.actionProductsFragmentToDetalleProductoFragment(
                            product.toJson()
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
    }


    private fun showDialogConfirmationAddProduct() {
        showSuccessMessage(getString(R.string.product_add))
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.products,
            onLoading = { skeleton?.showSkeleton() },
            onFinishLoading = { skeleton?.showOriginal() }
        ) {
            binding.recyclerProducts.adapter = adapter
            adapter?.setData(it)
            binding.root.setBackgroundColor(resources.getColor(R.color.background))
        }
    }


    override fun clickOnItem(element: Product, position: Int?) {
        val action = ProductsFragmentDirections.actionProductsFragmentToDetalleProductoFragment(element.toJson())
        navigate(action)
    }


}