package com.example.citassalon.presentacion.features.info.productos.detalleproducto

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.fromJson
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.Product
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

class DetalleProductoFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {

    private val args: DetalleProductoFragmentArgs by navArgs()
    private lateinit var product: Product

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = "Detalle producto"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                product = args.producto.fromJson()
                DetailProductView(product)
            }
        }
    }

    @Composable
    fun DetailProductView(product: Product) {
        var rating: Float by remember { mutableFloatStateOf(product.rating.rate.toFloat()) }
        Column(
            Modifier
                .fillMaxSize()
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = product.image,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    DText(text = product.title, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    RatingBar(value = rating, style = RatingBarStyle.Fill(), onValueChange = {
                        rating = it
                    }, onRatingChanged = {

                    })
                    Spacer(modifier = Modifier.height(8.dp))
                    val price = "$ ${product.price}"
                    DText(text = price)
                    Spacer(modifier = Modifier.height(8.dp))
                    DText(
                        text = product.description, modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    fun DText(modifier: Modifier = Modifier, text: String, fontWeight: FontWeight? = null) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = fontWeight,
            color = AlwaysBlack,
            modifier = modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            textAlign = TextAlign.Center
        )
    }

    @Composable
    @Preview(showBackground = true)
    fun DetailProductViewPreview() {
        DetailProductView(args.producto.fromJson<Product>())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    @SuppressLint("SetTextI18n")
    override fun setUpUi() {

    }

}