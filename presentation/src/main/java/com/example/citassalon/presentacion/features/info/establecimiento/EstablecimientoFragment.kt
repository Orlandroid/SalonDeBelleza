package com.example.citassalon.presentacion.features.info.establecimiento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.theme.Background


class EstablecimientoFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {
    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.nombre_establecimiento)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                EstablishmentScreen()
            }
        }
    }

    override fun setUpUi() {

    }

    @Composable
    fun EstablishmentScreen() {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            val guideline = createGuidelineFromTop(0.4f)
            val (text, list) = createRefs()
            Text(text = stringResource(id = R.string.nombre_establecimiento),
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(text) {
                    linkTo(parent.top, guideline)
                    linkTo(parent.start, parent.end)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                })
            Card(
                Modifier.constrainAs(list) {
                    linkTo(guideline, parent.bottom)
                    linkTo(parent.start, parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LazyColumn {
                    item {
                        TextWithArrow(
                            config = TextWithArrowConfig(
                                text = stringResource(id = R.string.sucursales),
                                clickOnItem = { navigate(EstablecimientoFragmentDirections.actionEstablecimientoToSucursales2()) }
                            )
                        )
                    }
                    item {
                        TextWithArrow(config = TextWithArrowConfig(
                            text = stringResource(id = R.string.tiendas),
                            clickOnItem = { navigate(EstablecimientoFragmentDirections.actionEstablecimientoToStoresFragment()) }
                        )
                        )
                    }
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun EstablishmentScreenPreview() {
        EstablishmentScreen()
    }


}