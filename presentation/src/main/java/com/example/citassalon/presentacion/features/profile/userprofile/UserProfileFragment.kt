package com.example.citassalon.presentacion.features.profile.userprofile


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUserProfileBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.GenericResultState
import com.example.citassalon.presentacion.features.extensions.base64StringToBitmap
import com.example.citassalon.presentacion.features.extensions.showSuccessMessage
import com.example.citassalon.presentacion.features.extensions.toBase64
import com.example.citassalon.presentacion.features.extensions.uriToBitmap
import com.example.citassalon.presentacion.features.theme.Background
import com.example.data.preferences.LoginPreferences
import com.example.domain.perfil.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(R.layout.fragment_generic_binding) {

    @Inject
    lateinit var loginPreferences: LoginPreferences

    private val viewModel: UserProfileViewModel by viewModels()
    private var imageUser: MutableState<Bitmap>? = null
    private var colorSessionUser: MutableState<Color> = mutableStateOf(Color.Red)

    @SuppressLint("MutableCollectionMutableState")
    private var listUserInfo: MutableState<ArrayList<UserInfo>> = mutableStateOf(arrayListOf())

//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true,
//        toolbarTitle = getString(R.string.perfil)
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserInfo()
        viewModel.getUserImage()
    }

    companion object {
        const val USER_EMAIL = "email"
        const val USER_UID = "uid"
        const val USER_SESSION = "userSession"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                UserProfileScreen()
            }
        }
    }

    @Composable
    fun UserProfileScreen() {
        val context = LocalContext.current
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageUri = it
                    val imageBitmap = context.uriToBitmap(it)
                    imageBitmap?.let {
                        viewModel.saveImageUser(imageBitmap.toBase64())
                    }
                }
            }
        )
        val infoUserState = viewModel.infoUser.observeAsState()
        val imageUserState = viewModel.imageUser.observeAsState()
        val imageUserProfileState = viewModel.imageUserProfile.observeAsState()
        Column(
            Modifier
                .fillMaxSize()
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            imageUser?.value?.let { image ->
                ImageUser(model = image, galleryLauncher)
            }
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = imageUri, contentDescription = "ImageProfile", modifier = Modifier
                    .size(144.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Nombre del usuario", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                CircleStatus(colorSessionUser.value)
            }
            GenericResultState(infoUserState) { data ->
                data.let { userResponse ->
                    listUserInfo.value.add(UserInfo("Nombre"))
                    listUserInfo.value.add(UserInfo("Telefono"))
                    listUserInfo.value.add(UserInfo("correo", userResponse[USER_EMAIL] ?: ""))
                    listUserInfo.value.add(UserInfo("uid", userResponse[USER_UID] ?: ""))
                    listUserInfo.value.add(
                        UserInfo(
                            "Money",
                            "$ " + loginPreferences.getUserMoney().toString()
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
                            listUserInfo.value.forEach { userInfo ->
                                item {
                                    ItemList(userInfo = userInfo)
                                }
                            }
                        }
                    }
                }
            }
            GenericResultState(imageUserState) {
                showSuccessMessage()
            }
            GenericResultState(imageUserProfileState) { dataResponse ->
                dataResponse?.let {
                    imageUser?.value = dataResponse.base64StringToBitmap()
                    imageUser?.value = dataResponse.base64StringToBitmap()
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
    fun CircleStatus(statusColor: Color) {
        androidx.compose.foundation.Canvas(modifier = Modifier.size(26.dp)) {
            drawCircle(color = statusColor)
        }
    }

    @Composable
    fun ImageUser(
        model: Any,
        galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = model, contentDescription = "ImageProfile", modifier = Modifier
                .size(144.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable {
                    galleryLauncher.launch("image/*")
                }
        )
    }

    override fun setUpUi() {

    }


    override fun observerViewModel() {
        super.observerViewModel()
//        observeApiResultGeneric(
//            liveData = viewModel.infoUser,
//            shouldCloseTheViewOnApiError = true,
//            hasProgressTheView = true,
//            onLoading = { binding.skeletonInfo.showSkeleton() },
//            onFinishLoading = { binding.skeletonInfo.showOriginal() }
//        ) {
//            listUserInfo.value.add(UserInfo("Nombre"))
//            listUserInfo.value.add(UserInfo("Telefono"))
//            listUserInfo.value.add(UserInfo("correo", it[USER_EMAIL] ?: ""))
//            listUserInfo.value.add(UserInfo("uid", it[USER_UID] ?: ""))
//            listUserInfo.value.add(
//                UserInfo(
//                    "Money",
//                    "$ " + loginPreferences.getUserMoney().toString()
//                )
//            )
//            if (it[USER_SESSION].equals("true")) {
//                colorSessionUser.value = "#239b56"
//            } else {
//                colorSessionUser.value = "#aab7b8"
//            }
//        }
//        observeApiResultGeneric(liveData = viewModel.imageUser) {
//            showSuccessMessage()
//        }
//        observeApiResultGeneric(
//            liveData = viewModel.imageUserProfile,
//            shouldCloseTheViewOnApiError = true,
//            hasProgressTheView = false
//        ) {
//            it.makeSaveAction {
//                Glide.with(requireContext()).load(it!!.base64StringToBitmap())
//                    .placeholder(R.drawable.loading_animation)
//                    .transition(DrawableTransitionOptions.withCrossFade()).circleCrop()
//                    .into(binding.imageUser)
//            }
//        }
    }

}