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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.citassalon.presentacion.features.base.ObserveBaseState
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.dialogs.AlertDialogMessagesConfig
import com.example.citassalon.presentacion.features.extensions.base64StringToBitmap
import com.example.citassalon.presentacion.features.extensions.toBase64
import com.example.citassalon.presentacion.features.extensions.uriToBitmap
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.perfil.UserInfo
import com.example.domain.perfil.UserProfileResponse

const val USER_EMAIL = "email"
const val USER_UID = "uid"
const val USER_SESSION = "userSession"

@Composable
fun UserProfileScreen(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val infoUserState = userProfileViewModel.infoUserState.collectAsStateWithLifecycle()
    val localImageState = userProfileViewModel.localImageState.collectAsStateWithLifecycle()
    val remoteImageState =
        userProfileViewModel.remoteImageProfileState.collectAsStateWithLifecycle()
    val imageUserRemote = remember { mutableStateOf<Bitmap?>(null) }
    val imageFromLocal = remember { mutableStateOf<Uri?>(null) }
    val userProfileResponse = remember { mutableStateOf<UserProfileResponse?>(null) }
    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageFromLocal.value = it
                    val imageBitmap = context.uriToBitmap(uri)
                    imageBitmap?.let {
                        userProfileViewModel.saveImageUser(imageLikeBase64 = imageBitmap.toBase64())
                    }
                }
            })
    LaunchedEffect(Unit) {
        userProfileViewModel.getUserImage()
        userProfileViewModel.getUserInfo()
    }
    ObserveBaseState(
        state = remoteImageState.value, alertDialogMessagesConfig = AlertDialogMessagesConfig()
    ) { response ->
        response.let {
            imageUserRemote.value = response.base64StringToBitmap()
        }
    }
    ObserveBaseState(
        state = localImageState.value, alertDialogMessagesConfig = AlertDialogMessagesConfig()
    ) {
        ///Add message image uploaded succesful
    }
    ObserveBaseState(
        state = infoUserState.value, alertDialogMessagesConfig = AlertDialogMessagesConfig()
    ) { userResponse ->
        userProfileResponse.value = userResponse
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            LazyColumn {
                userResponse.userInfo.forEach { userInfo ->
                    item {
                        ItemList(userInfo = userInfo)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
    BaseComposeScreen(
        navController = navController, toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true, title = stringResource(id = R.string.userProfile)
        )
    ) {
        UserProfileScreenContent(
            imageUserRemote = imageUserRemote.value,
            launchGallery = { galleryLauncher.launch("image/*") },
            isSessionActive = userProfileResponse.value?.isUserSessionActive ?: false,
            userInfo = userProfileResponse.value?.userInfo ?: emptyList()
        )
    }
}


@Composable
fun UserProfileScreenContent(
    modifier: Modifier = Modifier,
    imageUserRemote: Bitmap? = null,
    launchGallery: () -> Unit,
    isSessionActive: Boolean,
    userInfo: List<UserInfo>,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
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
            CircleStatus(
                if (isSessionActive) {
                    Color.Green
                } else {
                    Color.Red
                }
            )

        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            LazyColumn {
                userInfo.forEach { userInfo ->
                    item {
                        ItemList(userInfo = userInfo)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun ItemList(userInfo: UserInfo) {
    Text(
        modifier = Modifier.padding(4.dp), text = userInfo.title, fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier.padding(4.dp), text = userInfo.value
    )
}


@Composable
fun ImageUser(
    model: Any,
    launchGallery: () -> Unit,
) {
    if (model is ImageBitmap) {
        Image(contentScale = ContentScale.Crop,
            bitmap = model,
            contentDescription = "ImageProfile",
            modifier = Modifier
                .size(144.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable {
                    launchGallery.invoke()
                })
    } else {
        AsyncImage(contentScale = ContentScale.Crop,
            model = model,
            contentDescription = "ImageProfile",
            modifier = Modifier
                .size(144.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable {
                    launchGallery.invoke()
                })
    }
}

@Composable
fun CircleStatus(statusColor: Color) {
    Canvas(modifier = Modifier.size(26.dp)) {
        drawCircle(color = statusColor)
    }
}

@Composable
@Preview(showBackground = true)
fun UserProfileScreenContentPreview(modifier: Modifier = Modifier) {
    UserProfileScreenContent(
        launchGallery = {},
        isSessionActive = true,
        userInfo = listOf(
            UserInfo("Nombre", "Orlando"),
            UserInfo("Telefono", "1234567890"),
            UserInfo("Correo", "email@gmail.com"),
            UserInfo("Money", "500 $")
        )
    )
}