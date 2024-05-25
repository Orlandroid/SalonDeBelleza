package com.example.citassalon.presentacion.features.info.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.base64StringToBitmap
import com.example.citassalon.presentacion.features.extensions.getUserMoneyFormat
import com.example.citassalon.presentacion.features.extensions.showProgress
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.WARNING_MESSAGE
import com.example.data.preferences.LoginPreferences
import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.Product
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ClickOnItem<Product> {

    @Inject
    lateinit var loginPreferences: LoginPreferences

    private val viewModel: CartViewModel by viewModels()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getUserMoneyFormat(loginPreferences.getUserMoney())
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CardScreen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgress()
    }

    @Composable
    @Preview(showBackground = true)
    fun CardScreenPreview() {
        CardScreen()
    }

    @Composable
    @Preview(showBackground = true)
    fun PreviewItemCart() {
        ItemCart(ProductDb(0, "", 0.0, "", "", "", 0.0, 1, ""))
    }

    @Composable
    fun CardScreen() {
        val allProducts = viewModel.allIProducts.observeAsState()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            val (content, list) = createRefs()
            LazyColumn(modifier = Modifier.constrainAs(list) {

            }) {
                allProducts.value?.let { listProducts ->
                    if (listProducts.isNotEmpty()) {
                        listProducts.forEach { product ->
                            item {
                                ItemCart(productDb = product)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ItemCart(productDb: ProductDb) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AlwaysWhite
            )
        ) {
            ConstraintLayout {
                val (image, name, price) = createRefs()
                Image(
                    modifier = Modifier.constrainAs(image) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        width = Dimension.value(100.dp)
                        height = Dimension.value(100.dp)
                    },
                    bitmap = productDb.image.base64StringToBitmap().asImageBitmap(),
                    contentDescription = "Image of product"
                )
                Text(
                    text = productDb.title,
                    modifier = Modifier
                        .constrainAs(name) {
                            start.linkTo(image.end)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.value(120.dp)
                            height = Dimension.wrapContent
                        }
                )
                Text(text = "$ ${productDb.price}",
                    modifier = Modifier.constrainAs(price) {
                        linkTo(parent.top, parent.bottom)
                        start.linkTo(name.end)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    })
            }
        }
    }


    override fun setUpUi() {/*
        with(binding) {
            progressBar.visible()/*
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
        }*/
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

    override fun observerViewModel() {/*
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
        }*/
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