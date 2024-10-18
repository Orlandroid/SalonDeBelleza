package com.example.citassalon.presentacion.features.info.stores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.extensions.toJson
import com.example.citassalon.presentacion.features.theme.Background
import kotlin.random.Random


class StoresFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {

//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true, toolbarTitle = getString(R.string.tiendas)
//    )

    companion object {
        const val FAKE_STORE = "Fake store"
        const val DUMMY_JSON = "DummyJSON"
    }

    override fun setUpUi() {

    }

    private fun clickOnStore(store: Store) {
//        findNavController().navigate(
//            StoresFragmentDirections.actionStoresFragmentToListOfProductsFragment(
//                store.toJson()
//            )
//        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                StoresScreen()
            }
        }
    }

    @Composable
    fun StoresScreen() {
        Column(
            Modifier.fillMaxSize().background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(setAnimation()))
            LottieAnimation(
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
            LazyColumn(Modifier.background(Color.White)) {
                setStores().forEach { store ->
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        TextWithArrow(
                            config = TextWithArrowConfig(text = store.name, clickOnItem = {
                                clickOnStore(store)
                            })
                        )
                    }
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun StoresScreenPreview() {
        StoresScreen()
    }

    private fun setStores() = listOf(
        Store(name = FAKE_STORE), Store(name = DUMMY_JSON)
    )

    private fun setAnimation(): Int {
        val random = Random.nextInt(1, 2)
        return if (random % 2 == 0) {
            R.raw.ecomerce
        } else {
            R.raw.ecomerce2
        }
    }

    data class Store(
        val name: String = ""
    )

}