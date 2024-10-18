package com.example.citassalon.presentacion.features.info.sucursal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.theme.Background

class NegocioInfoFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {

//    private val args: NegocioInfoFragmentArgs by navArgs()

//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true, toolbarTitle = "Sucursal"
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                BusinessInfo()
            }
        }
    }

    override fun setUpUi() {

    }

    @Composable
    fun BusinessInfo() {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            val myGuideline = createGuidelineFromTop(0.40f)
            val (content) = createRefs()
            Card(
                Modifier.constrainAs(content) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(myGuideline)
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(parent.bottom)
                }, colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LazyColumn {
                    item {
                        TextWithArrow(config = TextWithArrowConfig(
                            text = context?.getString(R.string.staff).toString()
                        ) {
//                            val action =
//                                NegocioInfoFragmentDirections.actionNegocioInfoToNuestroStaff()
//                            navigate(action)
                        })
                    }
                    item {
                        TextWithArrow(config = TextWithArrowConfig(
                            text = context?.getString(R.string.servicios).toString()
                        ) {
//                            val action =
//                                NegocioInfoFragmentDirections.actionNegocioInfoToInfoServicios()
//                            navigate(action)
                        })
                    }
                    item {
                        TextWithArrow(config = TextWithArrowConfig(
                            text = context?.getString(R.string.ubicacion).toString()
                        ) {
//                            val action =
//                                NegocioInfoFragmentDirections.actionNegocioInfoToUbicacion(args.currentSucursal)
//                            navigate(action)
                        })
                    }
                }
            }
        }
    }

}