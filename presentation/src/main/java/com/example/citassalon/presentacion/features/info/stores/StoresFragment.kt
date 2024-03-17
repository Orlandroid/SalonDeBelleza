package com.example.citassalon.presentacion.features.info.stores

import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentStoresBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.toJson
import kotlin.random.Random


class StoresFragment : BaseFragment<FragmentStoresBinding>(R.layout.fragment_stores) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.tiendas)
    )

    companion object {
        const val FAKE_STORE = "Fake store"
        const val DUMMY_JSON = "DummyJSON"
    }

    private val adapter = StoresAdapter(clickOnStore = { clickOnStore(it) })
    override fun setUpUi() {
        setAnimation()
        binding.recycler.adapter = adapter
        adapter.setData(setStores())
    }

    private fun clickOnStore(store: StoresAdapter.Store) {
        findNavController().navigate(
            StoresFragmentDirections.actionStoresFragmentToListOfProductsFragment(
                store.toJson()
            )
        )
    }

    private fun setStores() =
        listOf(
            StoresAdapter.Store(name = FAKE_STORE),
            StoresAdapter.Store(name = DUMMY_JSON)
        )

    private fun setAnimation() {
        val random = Random.nextInt(1, 2)
        if (random % 2 == 0) {
            binding.lottieAnimation.setAnimation(R.raw.ecomerce)
        } else {
            binding.lottieAnimation.setAnimation(R.raw.ecomerce2)
        }
    }
}