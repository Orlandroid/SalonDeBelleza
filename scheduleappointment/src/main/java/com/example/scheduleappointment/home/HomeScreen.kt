package com.example.scheduleappointment.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.theme.AlwaysBlack
import com.example.core.ui.theme.AlwaysWhite
import com.example.core.ui.theme.Background
import com.example.core.ui.theme.BackgroundListsMainFlow
import com.example.core.ui.theme.StatusBarColor
import com.example.core.util.toCurrencyString
import com.example.domain.wallet.Currency
import com.example.scheduleappointment.R
import com.example.scheduleappointment.components.WalletCardSkeletons


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), event: (HomeScreenEvents) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    BackHandler {
        event(HomeScreenEvents.OnCloseScreen)
    }
    HomeScreenContent(
        modifier = Modifier,
        walletBalance = uiState.balance.toCurrencyString(Currency.USD),
        event = event,
        isLoading = uiState.isLoading
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    walletBalance: String,
    isLoading: Boolean,
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
                .fillMaxHeight(0.3f)
                .fillMaxWidth()
                .padding(24.dp)
        )
        Spacer(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth()
        )
        Text(
            fontSize = 30.sp,
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
        )
        Text(
            text = stringResource(R.string.home_message),
            fontSize = 16.sp,
            letterSpacing = (-0.5).sp
        )
        Spacer(
            Modifier
                .weight(0.4f)
                .fillMaxWidth()
        )
        if (isLoading) {
            WalletCardSkeletons()
        } else {
            WalletCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                balance = walletBalance,
                onClick = {
                    event(HomeScreenEvents.NavigateToWallet)
                })
        }
        Spacer(
            Modifier
                .weight(0.6f)
                .fillMaxWidth()
        )
        ButtonSchedule(
            modifier = Modifier, event = {
                event(HomeScreenEvents.NavigateToChoseBranch)
            })
        Spacer(
            Modifier
                .weight(0.6f)
                .fillMaxWidth()
        )
        ContainerFloatingButtons {
            Spacer(Modifier.width(24.dp))
            FloatingButtonInfo(
                modifier = Modifier, goToInfoNavigation = {
                    event(HomeScreenEvents.NavigateToInfoNavigationFlow)
                })
            Spacer(Modifier.weight(1f))
            FloatingButtonProfile(
                modifier = Modifier, goToProfileNavigation = {
                    event(HomeScreenEvents.NavigateToProfile)
                })
            Spacer(Modifier.width(24.dp))
        }
    }
}

@Composable
private fun WalletCard(
    modifier: Modifier = Modifier,
    balance: String, onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundListsMainFlow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.my_wallet),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = AlwaysBlack
            )
            Spacer(Modifier.padding(top = 12.dp))
            Text(
                text = balance, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AlwaysBlack
            )
            Spacer(Modifier.padding(top = 12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.show_wallet),
                    fontSize = 14.sp,
                    color = AlwaysBlack
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
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
    modifier: Modifier = Modifier, event: (HomeScreenEvents) -> Unit
) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = AlwaysWhite
        ), onClick = {
            event(HomeScreenEvents.NavigateToChoseBranch)
        }, modifier = modifier
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
        modifier = modifier, onClick = goToInfoNavigation
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
        modifier = modifier, onClick = goToProfileNavigation
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
        walletBalance = "$450.00 USD",
        event = {},
        isLoading = true
    )
}
