package com.example.citassalon.presentacion.features.info.sucursales

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.extensions.GenericResultState
import com.example.citassalon.presentacion.features.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.features.share_beetwen_sucursales.BranchViewModel
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.domain.entities.remote.migration.NegoInfo
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SucursalesFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ClickOnItem<NegoInfo> {

    private val viewModel: BranchViewModel by viewModels()

//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true,
//        toolbarTitle = getString(R.string.ubicacion)
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBranches()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                BranchOffices(viewModel)
            }
        }
    }

    @Composable
    fun BranchOffices(viewModel: BranchViewModel) {
        //Todo add skeletons in loading state
        val branches = viewModel.branches.observeAsState()
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            val guideline = createGuidelineFromTop(0.4f)
            val (list, text) = createRefs()
            Text(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.sucursales),
                modifier = Modifier.constrainAs(text) {
                    linkTo(parent.top, guideline)
                    linkTo(parent.start, parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
            Card(
                modifier = Modifier.constrainAs(list) {
                    linkTo(guideline, parent.bottom)
                    linkTo(parent.start, parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                GenericResultState(state = branches) {
                    LazyColumn {
                        branches.value?.data?.forEach { bussnises ->
                            item {
                                TextWithArrow(
                                    config = TextWithArrowConfig(
                                        text = bussnises.sucursal.name,
                                        clickOnItem = {
                                            clickOnItem(bussnises)
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun BranchOfficesPreview() {
        BranchOffices(viewModel)
    }


    override fun setUpUi() {

    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.branches
        ) {
            //binding.shimmerSucursal.gone()
            //binding.recyclerView.adapter = SucursalAdapter(it, getListener())
        }
    }


    private fun getListener(): ClickOnItem<NegoInfo> = this


    override fun clickOnItem(element: NegoInfo, position: Int?) {
        val gson = Gson()
        val bussnicesInfo = gson.toJson(element)
//        val action = SucursalesFragmentDirections.actionSucursales2ToNegocioInfo(bussnicesInfo)
//        navigate(action)
    }

}