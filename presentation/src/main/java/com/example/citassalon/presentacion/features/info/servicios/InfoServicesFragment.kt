package com.example.citassalon.presentacion.features.info.servicios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment


class InfoServicesFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {

//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true,
//        toolbarTitle = "Servicios"
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                InfoServicesScreen()
            }
        }
    }

    @Composable
    fun InfoServicesScreen(modifier: Modifier = Modifier) {
        Text(text = "InfoServiceScreen")
    }

    override fun setUpUi() {

    }

}