package com.example.citassalon.presentacion.features.info.productos.detalleproducto

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentDetalleProductoBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.fromJson
import com.example.domain.entities.remote.Product

class DetalleProductoFragment :
    BaseFragment<FragmentDetalleProductoBinding>(R.layout.fragment_detalle_producto) {

    private val args: DetalleProductoFragmentArgs by navArgs()
    lateinit var product: Product

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Detalle producto"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    @SuppressLint("SetTextI18n")
    override fun setUpUi() {
        with(binding) {
            initRating()
            product = args.producto.fromJson()
            tvTitle.text = product.title
            Glide.with(this@DetalleProductoFragment).load(product.image).into(image)
            rating.rating = product.rating.rate.toFloat()
            tvPrecio.text = "$ ${product.price}"
            tvDescripcion.text = product.description
        }
    }

    private fun initRating() {
        with(binding) {
            rating.setIsIndicator(true)
            rating.numStars = 5
        }
    }

}