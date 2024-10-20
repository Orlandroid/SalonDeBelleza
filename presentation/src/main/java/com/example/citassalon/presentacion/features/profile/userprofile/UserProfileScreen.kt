package com.example.citassalon.presentacion.features.profile.userprofile

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.extensions.GenericResultStateV2
import com.example.citassalon.presentacion.features.extensions.base64toBitmap
import com.example.citassalon.presentacion.features.extensions.toBase64
import com.example.citassalon.presentacion.features.extensions.uriToBitmap
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.perfil.UserInfo
import com.example.domain.state.ApiState

const val USER_EMAIL = "email"
const val USER_UID = "uid"
const val USER_SESSION = "userSession"

@Composable
fun UserProfileScreen(
    navController: NavHostController,
) {
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val infoUserState = userProfileViewModel.infoUser.observeAsState()
    val imageUserState = userProfileViewModel.imageUser.observeAsState()
    val imageUserProfileState = userProfileViewModel.imageUserProfile.observeAsState()
    LaunchedEffect(Unit) {
        userProfileViewModel.getUserInfo()
        userProfileViewModel.getUserImage()
    }
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true,
            title = stringResource(id = R.string.userProfile)
        )
    ) {
        UserProfileScreenContent(
            infoUserState = infoUserState,
            saveImageUser = {},
            imageUserState = imageUserState,
            imageUserProfileState = imageUserProfileState,
            money = userProfileViewModel.getUserMoney()
        )
    }
}


@Composable
fun UserProfileScreenContent(
    modifier: Modifier = Modifier,
    infoUserState: State<ApiState<HashMap<String, String>>?>,
    imageUserState: State<ApiState<String>?>,
    imageUserProfileState: State<ApiState<String?>?>,
    money: String,
    saveImageUser: (imageUserBase64: String) -> Unit
) {
    val listUserInfo = arrayListOf<UserInfo>()
    val context = LocalContext.current
    val imageUser = remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val colorSessionUser = remember { mutableStateOf(Color.Red) }
    val isLoading = remember { mutableStateOf(false) }
    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageUri = it
                    val imageBitmap = context.uriToBitmap(it)
                    imageBitmap?.let {
                        saveImageUser(imageBitmap.toBase64())
                    }
                }
            }
        )
    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        imageUser.value?.let { userImage ->
            ImageUser(model = userImage, galleryLauncher)
        }
        imageUri?.let { userStoreImage ->
            AsyncImage(contentScale = ContentScale.Crop,
                model = userStoreImage,
                contentDescription = "ImageProfile",
                modifier = Modifier
                    .size(144.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Nombre del usuario", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(24.dp))
            CircleStatus(colorSessionUser.value)
        }
        Spacer(modifier = Modifier.height(24.dp))
        GenericResultStateV2(state = infoUserState, isLoading = isLoading) { data ->
            data.let { userResponse ->
                listUserInfo.add(UserInfo("Nombre"))
                listUserInfo.add(UserInfo("Telefono"))
                listUserInfo.add(UserInfo("correo", userResponse[USER_EMAIL] ?: ""))
                listUserInfo.add(UserInfo("uid", userResponse[USER_UID] ?: ""))
                listUserInfo.add(
                    UserInfo(
                        "Money", "$ $money"
                    )
                )
                if (data[USER_SESSION].equals("true")) {
                    colorSessionUser.value = Color.Green
                } else {
                    colorSessionUser.value = Color.Red
                }
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()

                ) {
                    LazyColumn(Modifier.padding(horizontal = 8.dp)) {
                        listUserInfo.forEach { userInfo ->
                            item {
                                ItemList(userInfo = userInfo)
                            }
                        }
                    }
                }
            }
        }
        GenericResultStateV2(state = imageUserState, isLoading = isLoading) {
            Log.w("ANDORID", "SUCCESS")
        }
        GenericResultStateV2(state = imageUserProfileState, isLoading = isLoading) { dataResponse ->
            dataResponse?.let {
                LaunchedEffect(imageUser.value) {
                    imageUser.value = dataResponse.base64toBitmap()
                    Log.w("ANDORID", "eRROR")
                }
            }
        }
    }
}

@Composable
fun ItemList(userInfo: UserInfo) {
    Column {
        Text(text = userInfo.title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = userInfo.value)
    }
}


@Composable
fun ImageUser(
    model: Any,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    AsyncImage(contentScale = ContentScale.Crop,
        model = model,
        contentDescription = "ImageProfile",
        modifier = Modifier
            .size(144.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
            .clickable {
                galleryLauncher.launch("image/*")
            }
    )
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
//    UserProfileScreenContent()
}