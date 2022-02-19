package com.example.citassalon.ui.info.productos.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentListOfProductsBinding
import com.example.citassalon.util.AlertDialogs
import com.example.citassalon.util.navigate
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfCategoriesFragment : Fragment(), ListOfCategoriesAdapter.ListOfcategoriesListener {

    private var _binding: FragmentListOfProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListOfCategoriesViewModel by viewModels()
    private val adapter = ListOfCategoriesAdapter(this)
    private lateinit var skeleton: Skeleton

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
        viewModel.getCategories()
    }

    private fun setUpObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { apiState ->
            when (apiState) {
                is ApiState.Loading -> {

                }
                is ApiState.Success -> {
                    if (apiState.data != null) {
                        binding.recyclerViewCategorias.adapter = adapter
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
        val action =
            ListOfCategoriesFragmentDirections.actionListOfProductsFragmentToProductsFragment(
                categoria
            )
        navigate(action)
    }


}