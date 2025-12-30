package com.example.citassalon.presentacion.features.profile.userprofile

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.BaseScreenStateV2
import com.example.citassalon.presentacion.features.base.getContentOrNull
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.components.skeletons.UserProfileScreenSkeleton
import com.example.citassalon.presentacion.features.extensions.uriToBitmap
import com.example.citassalon.presentacion.features.theme.Background


@Composable
fun UserProfileScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val uiState = userProfileViewModel.state.collectAsStateWithLifecycle()
    val imageUserRemote = remember { mutableStateOf<Bitmap?>(null) }
    val imageFromLocal = remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), onResult = { uri ->
            uri?.let {
                imageFromLocal.value = it
                val imageBitmap = context.uriToBitmap(uri)
                imageBitmap?.let {
//                    userProfileViewModel.saveImageUser(imageLikeBase64 = imageBitmap.toBase64())
                }
            }
        }
    )
    BaseComposeScreen(
        navController = navController, toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true, title = stringResource(id = R.string.userProfile)
        )
    ) {
        when (uiState.value) {
            BaseScreenStateV2.OnLoading -> {
                UserProfileScreenSkeleton()
            }

            is BaseScreenStateV2.OnContent<*> -> {
                uiState.value.getContentOrNull()?.let { state ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        UserProfileScreenContent(
                            imageUserRemote = imageUserRemote.value,
                            launchGallery = { galleryLauncher.launch("image/*") },
                            userProfileState = state
                        )
                    }
                }
            }

            is BaseScreenStateV2.OnError -> {
                //OnError
            }
        }
    }
}


@Composable
private fun UserProfileScreenContent(
    modifier: Modifier = Modifier,
    imageUserRemote: Bitmap? = null,
    launchGallery: () -> Unit,
    userProfileState: UserProfileUiState
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageUserRemote == null) {
            ImageUser(model = R.drawable.userprofile) {
                launchGallery.invoke()
            }
        } else {
            ImageUser(model = imageUserRemote.asImageBitmap()) {
                launchGallery.invoke()
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.name_user),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(24.dp))
            CircleStatus(statusColor = Color.Green)

        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            BaseLabel(key = stringResource(R.string.name_user), value = userProfileState.name)
            HorizontalDivider()
            BaseLabel(key = stringResource(R.string.phone), value = userProfileState.phone)
            HorizontalDivider()
            BaseLabel(key = stringResource(R.string.label_email), value = userProfileState.email)
            HorizontalDivider()
            BaseLabel(key = stringResource(R.string.money), value = userProfileState.money)
            HorizontalDivider()
        }
    }
}

@Composable
private fun BaseLabel(
    key: String,
    value: String?
) {
    Text(
        modifier = Modifier.padding(4.dp),
        text = key,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier.padding(4.dp),
        text = value.orEmpty()
    )
}


@Composable
private fun ImageUser(
    model: Any,
    launchGallery: () -> Unit
) {
    val imageModifier =
        Modifier
            .size(144.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
            .clickable {
                launchGallery.invoke()
            }
    if (model is ImageBitmap) {
        Image(
            contentScale = ContentScale.Crop,
            bitmap = model,
            contentDescription = "ImageProfile",
            modifier = imageModifier
        )
    } else {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = model,
            contentDescription = "ImageProfile",
            modifier = imageModifier
        )
    }
}

@Composable
private fun CircleStatus(statusColor: Color) {
    Canvas(modifier = Modifier.size(26.dp)) {
        drawCircle(color = statusColor)
    }
}


@Composable
@Preview(showBackground = true)
private fun UserProfileScreenContentPreview() {
    UserProfileScreenContent(
        userProfileState = UserProfileUiState(
            name = "Orlando",
            "1234567890",
            email = "android@gmail.com",
            money = "500"

        ),
        launchGallery = {}
    )
}