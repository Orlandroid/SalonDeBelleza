package com.example.citassalon.ui.info.productos.detalleproducto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.citassalon.databinding.FragmentDetalleProductoBinding

class DetalleProductoFragment : Fragment() {

    private var _binding: FragmentDetalleProductoBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleProductoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleProductoBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Detalle producto"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}