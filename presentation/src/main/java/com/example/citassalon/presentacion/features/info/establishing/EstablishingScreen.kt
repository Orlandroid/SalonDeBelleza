package com.example.citassalon.presentacion.features.info.establishing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.info.InfoNavigationScreens
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun EstablishingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.nombre_establecimiento))
    ) {
        EstablishingScreenContent(
            modifier = modifier,
            navigateToStore = {
                navController.navigate(InfoNavigationScreens.StoresRoute)
            },
            navigateToBranches = {
                navController.navigate(InfoNavigationScreens.BranchesRoute)
            }
        )
    }
}

@Composable
fun EstablishingScreenContent(
    modifier: Modifier = Modifier,
    navigateToStore: () -> Unit,
    navigateToBranches: () -> Unit
) {
    ConstraintLayout(
        modifier
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
            }
        )
        Card(
            Modifier.constrainAs(list) {
                linkTo(guideline, parent.bottom)
                linkTo(parent.start, parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            MenuBranch(
                clickOnBranches = {
                    navigateToBranches.invoke()
                },
                clickOnStore = {
                    navigateToStore.invoke()
                }
            )
        }
    }
}

@Composable
private fun MenuBranch(
    clickOnBranches: () -> Unit,
    clickOnStore: () -> Unit
) {
    LazyColumn {
        item {
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = stringResource(id = R.string.sucursales),
                    clickOnItem = {
                        clickOnBranches.invoke()
                    }
                )
            )
        }
        item {
            TextWithArrow(
                config = TextWithArrowConfig(
                    text = stringResource(id = R.string.tiendas),
                    clickOnItem = {
                        clickOnStore.invoke()
                    }
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EstablishingScreenPreview() {
    EstablishingScreenContent(
        navigateToBranches = {},
        navigateToStore = {}
    )
}