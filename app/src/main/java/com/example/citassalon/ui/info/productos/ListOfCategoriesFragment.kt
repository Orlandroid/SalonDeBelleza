package com.example.citassalon.ui.info.productos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentListOfProductsBinding
import com.example.citassalon.util.AlertDialogs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfCategoriesFragment : Fragment(), ListOfCategoriesAdapter.ListOfcategoriesListener {

    private var _binding: FragmentListOfProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListOfCategoriesViewModel by viewModels()
    private val adapter = ListOfCategoriesAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfProductsBinding.inflate(inflater, container, false)
        setUpUi()
        setUpObservers()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Categorias"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        viewModel.getProducts()
    }

    private fun setUpObservers() {
        viewModel.products.observe(viewLifecycleOwner) { apiState ->
            when (apiState) {
                is ApiState.Loading -> {
                    Log.w("ANDROID", "Loading")
                }
                is ApiState.Success -> {
                    if (apiState.data != null) {
                        binding.recyclerViewProducts.adapter = adapter
                        adapter.setData(apiState.data)
                    }
                    Log.w("ANDROID", "Succes")
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

    override fun clikcOnCategorie(categoria: String) {
        Toast.makeText(requireContext(), "$categoria", Toast.LENGTH_SHORT).show()
    }


}