package com.example.citassalon.ui.info.productos.productos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citassalon.R
import com.example.citassalon.data.models.Products
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentProductsBinding
import com.example.citassalon.ui.info.productos.categories.ListOfCategoriesFragmentDirections
import com.example.citassalon.util.AlertDialogs
import com.example.citassalon.util.navigate
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(), ProductsAdapter.ProductsListener {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductsViewModel by viewModels()
    private val adapter = ProductsAdapter(this)
    private val args: ProductsFragmentArgs by navArgs()
    private lateinit var skeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObservers()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Productos"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            skeleton = recyclerProducts.applySkeleton(R.layout.item_product, 8)
            recyclerProducts.layoutManager =
                GridLayoutManager(requireContext(), 2)
        }
        viewModel.getProducts(args.categoria)
    }


    private fun setUpObservers() {
        viewModel.products.observe(viewLifecycleOwner) { apiState ->
            when (apiState) {
                is ApiState.Loading -> {
                    skeleton.showSkeleton()
                }
                is ApiState.Success -> {
                    skeleton.showOriginal()
                    if (apiState.data != null) {
                        binding.recyclerProducts.adapter = adapter
                        adapter.setData(apiState.data)
                    }
                }
                is ApiState.Error -> {
                    val dialog = AlertDialogs(2, "Error al obtener datos")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                }
                is ApiState.ErrorNetwork -> {
                    val dialog = AlertDialogs(2, "Verifica tu conexion de internet")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clikcOnProduct(product: Products) {
        val action = ProductsFragmentDirections.actionProductsFragmentToDetalleProductoFragment(product)
        navigate(action)
    }


}