package com.example.citassalon.presentacion.features.schedule_appointment.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.theme.AlwaysBlack
import com.example.citassalon.presentacion.features.theme.AlwaysWhite
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.features.theme.StatusBarColor

@Composable
fun HomeScreen(
    event: (HomeScreenEvents) -> Unit
) {
    BackHandler {
        event(HomeScreenEvents.OnCloseScreen)
    }
    HomeScreenContent(
        modifier = Modifier,
        event = event
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    event: (HomeScreenEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .background(StatusBarColor)
                .fillMaxHeight(0.4f)
                .fillMaxWidth()
                .padding(24.dp)
        )
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            fontSize = 30.sp,
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
        )
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        ButtonSchedule(
            modifier = Modifier,
            event = {
                event(HomeScreenEvents.NavigateToChoseBranch)
            }
        )
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        ContainerFloatingButtons {
            Spacer(Modifier.width(32.dp))
            FloatingButtonInfo(
                modifier = Modifier,
                goToInfoNavigation = {
                    event(HomeScreenEvents.NavigateToInfoNavigationFlow)
                }
            )
            Spacer(Modifier.weight(1f))
            FloatingButtonProfile(
                modifier = Modifier,
                goToProfileNavigation = {
                    event(HomeScreenEvents.NavigateToProfile)
                }
            )
            Spacer(Modifier.width(32.dp))
        }
    }
}


@Composable
private fun ContainerFloatingButtons(
    content: @Composable (RowScope.() -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 60.dp)
    ) {
        content()
    }
}

@Composable
private fun ButtonSchedule(
    modifier: Modifier = Modifier,
    event: (HomeScreenEvents) -> Unit
) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = AlwaysWhite
        ),
        onClick = {
            event(HomeScreenEvents.NavigateToChoseBranch)
        },
        modifier = modifier
    ) {
        Text(
            color = AlwaysBlack,
            text = stringResource(id = R.string.agendar_button),
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
private fun FloatingButtonInfo(
    modifier: Modifier = Modifier,
    goToInfoNavigation: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = goToInfoNavigation
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_list_24),
            contentDescription = null
        )
    }
}

@Composable
private fun FloatingButtonProfile(
    modifier: Modifier = Modifier,
    goToProfileNavigation: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = goToProfileNavigation
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_person_24),
            contentDescription = null
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    HomeScreenContent(
        event = {}
    )
}