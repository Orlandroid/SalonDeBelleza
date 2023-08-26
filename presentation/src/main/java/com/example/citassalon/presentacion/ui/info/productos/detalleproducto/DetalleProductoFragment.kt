package com.example.citassalon.presentacion.ui.info.productos.detalleproducto

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentDetalleProductoBinding
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment

class DetalleProductoFragment :
    BaseFragment<FragmentDetalleProductoBinding>(R.layout.fragment_detalle_producto) {

    private val args: DetalleProductoFragmentArgs by navArgs()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Detalle producto"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        with(binding) {
            initRating()
            tvTitle.text = args.producto.title
            Glide.with(this@DetalleProductoFragment).load(args.producto.image).into(image)
            rating.rating = args.producto.rating.rate.toFloat()
            tvPrecio.text = "$ ${args.producto.price}"
            tvDescripcion.text = args.producto.description
        }
    }

    private fun initRating() {
        with(binding) {
            rating.setIsIndicator(true)
            rating.numStars = 5
        }
    }

}