package com.example.citassalon.presentacion.features.info.nuestro_staff

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.BaseScreenStateV2
import com.example.citassalon.presentacion.features.base.getContentOrNull
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun OurStaffScreen(
    navController: NavController,
    ourStaffViewModel: OurStaffViewModel = hiltViewModel()
) {
    val uiState = ourStaffViewModel.state.collectAsStateWithLifecycle()
    when (uiState.value) {

        BaseScreenStateV2.OnLoading -> {
            ProgressDialog()
        }

        is BaseScreenStateV2.OnContent -> {
            BaseComposeScreen(
                navController = navController,
                toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.nuestro_staff)),
            ) {
                uiState.value.getContentOrNull()?.let {
                    OurStaffScreenContent(users = it.staffs.users.toUserUiList())
                }
            }
        }

        is BaseScreenStateV2.OnError -> {

        }
    }
}

@Composable
private fun OurStaffScreenContent(
    modifier: Modifier = Modifier,
    users: List<UserUi>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(Modifier.fillMaxHeight(0.15f)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.nustro_staff),
            )
        }
        Card(
            modifier = Modifier.fillMaxHeight(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            LazyColumn {
                users.forEach { user ->
                    item {
                        OurStaffItem(user)
                    }
                }
            }
        }
    }
}

@Composable
private fun OurStaffItem(user: UserUi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxHeight(),
                model = user.image,
                contentDescription = "ImageOurStaff",
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.email,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.address,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun OurStaffScreenContentPreview() {
    val user = UserUi(
        firstName = "Naruto",
        "Uzumaki",
        "android@gmail.com",
        address = "Mexico",
        image = ""
    )
    OurStaffScreenContent(
        users = listOf(
            user,
            user,
            user,
            user
        )
    )

}


