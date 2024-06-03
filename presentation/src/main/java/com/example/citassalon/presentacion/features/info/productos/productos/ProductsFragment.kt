package com.example.citassalon.presentacion.features.info.productos.productos

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentProductsBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.ButtonWithIcon
import com.example.citassalon.presentacion.features.extensions.GenericResultState
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.showSuccessMessage
import com.example.citassalon.presentacion.features.extensions.toBase64
import com.example.citassalon.presentacion.features.extensions.toJson
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.domain.entities.remote.Product
import com.example.domain.mappers.toProductDb
import com.example.domain.state.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>(R.layout.fragment_products),
    ClickOnItem<Product> {

    private val viewModel: ProductsViewModel by viewModels()
    private val args: ProductsFragmentArgs by navArgs()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.productos)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProducts(args.categoria)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProductsScreen(viewModel)
            }
        }
    }

    @Composable
    fun ProductsScreen(viewModel: ProductsViewModel) {
        val categories = viewModel.products.observeAsState()
        Column(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Image(
                    modifier = Modifier
                        .padding(end = 8.dp, top = 8.dp)
                        .size(50.dp)
                        .clickable {
                            val action =
                                ProductsFragmentDirections.actionProductsFragmentToCartFragment()
                            navigate(action)
                        },
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = "ImageCart"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ShowProducts(categories)
        }
    }

    @Composable
    fun ShowProducts(products: State<ApiState<List<Product>>?>) {
        //TODO Add Skeletons when loading
        GenericResultState(state = products) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                products.value?.data?.forEach { product ->
                    item {
                        ItemProduct(product = product)
                    }
                }
            }
        }
    }

    @Composable
    fun ItemProduct(modifier: Modifier = Modifier, product: Product) {
        val imageLoader =
            LocalContext.current.imageLoader.newBuilder().logger(DebugLogger()).build()
        var bitmapImageProduct: Bitmap? = null
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier.padding(4.dp),
            onClick = {
                clickOnItem(element = product)
            },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ButtonWithIcon(
                modifier = modifier.padding(horizontal = 8.dp),
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_baseline_add_24
                ),
                buttonText = "Agregar",
                backgroundColor = Color.White,
                onClick = {
                    product.imageBase64 = bitmapImageProduct?.toBase64() ?: ""
                    viewModel.insertProduct(product.toProductDb()) {
                        showDialogConfirmationAddProduct()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                model = product.image,
                contentDescription = "ImageProduct",
                imageLoader = imageLoader,
                onSuccess = {
                    bitmapImageProduct = it.result.drawable.toBitmapOrNull()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = product.title, Modifier.padding(horizontal = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = product.price.toString(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
        }
    }


    override fun setUpUi() {

    }


    private fun showDialogConfirmationAddProduct() {
        showSuccessMessage(getString(R.string.product_add))
    }

    override fun observerViewModel() {

    }


    override fun clickOnItem(element: Product, position: Int?) {
        navigate(ProductsFragmentDirections.actionProductsFragmentToDetalleProductoFragment(element.toJson()))
    }

    @Composable
    @Preview(showBackground = true)
    fun ProductsScreenPreview() {
        ProductsScreen(viewModel)
    }


}