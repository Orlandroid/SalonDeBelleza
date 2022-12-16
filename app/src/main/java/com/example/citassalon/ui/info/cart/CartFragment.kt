package com.example.citassalon.ui.info.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.mappers.toProduct
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.databinding.FragmentCartBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart),
    ClickOnItem<Product> {

    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter = CartAdapter()
    private var total = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Carrito"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            recyclerCart.adapter = cartAdapter
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.allIProducts.observe(this.viewLifecycleOwner) { items ->
            val listProducts = arrayListOf<Product>()
            items.forEach {
                listProducts.add(it.toProduct())
            }
            cartAdapter.setData(listProducts)
            Log.w("ANDORID", "ANDROID")
        }
    }


    override fun clikOnElement(element: Product, position: Int?) {

    }

}