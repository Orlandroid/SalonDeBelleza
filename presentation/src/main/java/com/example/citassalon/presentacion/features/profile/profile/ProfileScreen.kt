package com.example.citassalon.presentacion.features.profile.profile

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.MediumSpacer
import com.example.citassalon.presentacion.features.base.Orientation
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.dialogs.BaseAlertDialogMessages
import com.example.citassalon.presentacion.features.dialogs.IsTwoButtonsAlert
import com.example.citassalon.presentacion.features.dialogs.KindOfMessage
import com.example.citassalon.presentacion.features.profile.ProfileNavigationScreen
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.perfil.MENU
import com.example.domain.perfil.ProfileItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val onEvents = profileViewModel::onEvents
    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        profileViewModel.effects.collectLatest {
            when (it) {
                ProfileEffects.CloseAndOpenActivity -> {
                    activity.finish()
                }

                ProfileEffects.NavigateToContacts -> {
                    navController.navigate(ProfileNavigationScreen.ContactsRoute)
                }

                ProfileEffects.NavigateToHistory -> {
                    navController.navigate(ProfileNavigationScreen.AppointmentHistoryRoute)
                }

                ProfileEffects.NavigateToProfile -> {
                    navController.navigate(ProfileNavigationScreen.UserProfileRoute)
                }

                ProfileEffects.NavigateToTermAndCondictions -> {
                    navController.navigate(ProfileNavigationScreen.TermsAndConditionsRoute)
                }
            }
        }
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true,
            isWithBackIcon = true,
            title = stringResource(
                id = R.string.perfil
            )
        )
    ) {
        ProfileScreenContent(
            modifier = Modifier,
            email = uiState.user.orEmpty(),
            elementsProfile = ProfileMenuProvider.getMenusProfile(),
        ) { itemProfile ->
            when (itemProfile.menu) {
                MENU.PROFILE -> {
                    onEvents(ProfileEvents.OnProfileClicked)
                }

                MENU.HISTORY -> {
                    onEvents(ProfileEvents.OnHistoricalClicked)
                }

                MENU.CONTACTS -> {
                    onEvents(ProfileEvents.OnContactClicked)
                }

                MENU.TERMS_AND_CONDITIONS -> {
                    onEvents(ProfileEvents.OnTermAndCondictionsClicked)
                }

                MENU.CLOSE_SESSION -> {
                    onEvents(ProfileEvents.OnCloseSession)
                }
            }
        }
        if (uiState.showAlertCloseSession) {
            DialogCloseSession(onEvents = onEvents)
        }
    }
}


@Composable
private fun DialogCloseSession(
    onEvents: (event: ProfileEvents) -> Unit
) {
    BaseAlertDialogMessages(
        onDismissRequest = {
            onEvents(ProfileEvents.OnDismissDialog)
        },
        alertDialogMessagesConfig = AlertDialogMessagesConfig(
            title = R.string.cerrar_session,
            bodyMessage = stringResource(R.string.seguro_que_deseas_cerrar_sesion),
            kindOfMessage = KindOfMessage.WARING,
            onConfirmation = {
                onEvents(ProfileEvents.OnConfirmClicked)
            },
            isTwoButtonsAlert = IsTwoButtonsAlert(
                clickOnCancel = {
                    onEvents(ProfileEvents.OnCancel)
                }
            )
        )
    )
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    email: String,
    elementsProfile: List<ProfileItem>,
    clickOnItemProfile: (ProfileItem) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp),
                text = email,
                fontSize = 20.sp,
            )
        }
        LazyColumn(
            modifier = Modifier
                .border(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    border = BorderStroke(0.dp, Color.White)
                )
                .background(Color.White)
                .fillMaxHeight()
        ) {
            items(elementsProfile.size) {
                ItemProfile(elementProfile = elementsProfile[it]) {
                    clickOnItemProfile(elementsProfile[it])
                }
            }
        }
    }
}


@Composable
private fun ItemProfile(
    elementProfile: ProfileItem,
    clickOnItem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { clickOnItem.invoke() }
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MediumSpacer(orientation = Orientation.HORIZONTAL)
            Image(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
                painter = painterResource(id = elementProfile.image),
                contentDescription = null
            )
            MediumSpacer(orientation = Orientation.HORIZONTAL)
            Text(text = elementProfile.name, fontSize = 20.sp)
            Spacer(Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                contentDescription = null,
                tint = Color.Black
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        email = "android@gmail.com",
        elementsProfile = ProfileMenuProvider.getMenusProfile()
    )
}