package com.example.citassalon.ui.info.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.Product
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentCartBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart),ClickOnItem<Product> {

    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter = CartAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setUpObservers()
    }

    private fun setUpUi() {
        viewModel.getCart(generateRandomId())
        with(binding) {
            toolbarLayout.toolbarTitle.text="Carrito"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpObservers() {
        viewModel.cart.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {

                }
                is ApiState.Success -> {
                    if (it.data != null){
                        binding.recyclerCart.adapter=cartAdapter
                    }
                }
                is ApiState.Error -> {

                }
                is ApiState.ErrorNetwork -> {

                }
                is ApiState.NoData -> {

                }
            }
        }
    }

    private fun generateRandomId() = (1..6).random()

    override fun clikOnElement(element: Product, position: Int?) {

    }

}