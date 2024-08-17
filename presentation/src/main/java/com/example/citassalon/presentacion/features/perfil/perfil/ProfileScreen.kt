package com.example.citassalon.presentacion.features.perfil.perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.perfil.MENU
import com.example.domain.perfil.ProfileItem

@Composable
fun ProfileScreen(
    navController: NavHostController,
    firebaseEmail: String?,
    elementsMenu: List<ProfileItem>
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val (image, textUser, listProfile) = createRefs()
        val myGuideline = createGuidelineFromTop(0.40f)
        Image(painter = painterResource(id = R.drawable.perfil),
            contentDescription = null,
            modifier = Modifier.constrainAs(image) {
                linkTo(parent.start, parent.end)
                top.linkTo(parent.top, 24.dp)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            })
        Text(text = firebaseEmail ?: "",
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(textUser) {
                linkTo(parent.start, parent.end)
                top.linkTo(image.bottom)
                bottom.linkTo(listProfile.top)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            })
        LazyColumn(modifier = Modifier
            .border(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                border = BorderStroke(0.dp, Color.White)
            )
            .background(Color.White)
            .constrainAs(listProfile) {
                linkTo(parent.start, parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(myGuideline)
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
            }) {
            elementsMenu.forEach { itemProfile ->
                item {
                    ItemProfile(elementProfile = itemProfile) {
//                        clickOnItem(itemProfile)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemProfile(
    elementProfile: ProfileItem, clickOnItem: () -> Unit
) {
    Card(shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { clickOnItem.invoke() }) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (row, icon) = createRefs()
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(row) {
                    start.linkTo(parent.start)
                    end.linkTo(icon.start, 16.dp)
                    width = Dimension.matchParent
                }) {
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = elementProfile.image),
                    contentDescription = null,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = elementProfile.name, fontSize = 20.sp
                )
            }
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.constrainAs(icon) {
                    end.linkTo(parent.end, 16.dp)
                    linkTo(parent.top, parent.bottom)
                })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview(modifier: Modifier = Modifier) {
    val profileElement = listOf(ProfileItem(name = "", image = R.drawable.perfil, MENU.PROFILE))
    ProfileScreen(
        navController = rememberNavController(),
        elementsMenu = profileElement,
        firebaseEmail = "android@gmail.com",
    )
}