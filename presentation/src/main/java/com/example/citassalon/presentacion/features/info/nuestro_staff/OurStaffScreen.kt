package com.example.citassalon.presentacion.features.info.nuestro_staff

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreenState
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.dummyUsers.User

@Composable
fun OurStaffScreen(
    navController: NavController,
    ourStaffViewModel: OurStaffViewModel = hiltViewModel()
) {
    val staffs = ourStaffViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        ourStaffViewModel.getStaffsUsersV2()
    }
    BaseComposeScreenState(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.nuestro_staff)),
        state = staffs.value
    ) { result ->
        OurStaffScreenContent(users = result.users)
    }
}

@Composable
fun OurStaffScreenContent(modifier: Modifier = Modifier, users: List<User>?) {
    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val myGuideline = createGuidelineFromTop(0.15f)
        val (list, text) = createRefs()
        Text(
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.nustro_staff),
            modifier = Modifier.constrainAs(text) {
                linkTo(parent.start, parent.end)
                linkTo(parent.top, list.top)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )
        Card(
            Modifier.constrainAs(list) {
                linkTo(parent.start, parent.end)
                top.linkTo(myGuideline)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }, colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            LazyColumn {
                users?.forEach { user ->
                    item {
                        OurStaffItem(user)
                    }
                }
            }
        }
    }
}

@Composable
fun OurStaffItem(user: User) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp), border = BorderStroke(1.dp, Color.Black)
    ) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (image, colum) = createRefs()
            AsyncImage(model = user.image,
                contentDescription = "ImageOurStaff",
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                }
            )
            Column(verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(colum) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
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
                    text = user.address.address,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun OurStaffScreenContentPreview() {
    OurStaffScreenContent(users = emptyList())

}


