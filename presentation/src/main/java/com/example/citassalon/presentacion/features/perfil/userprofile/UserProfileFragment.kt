package com.example.citassalon.presentacion.features.perfil.userprofile


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUserProfileBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.GenericResultState
import com.example.citassalon.presentacion.features.extensions.base64StringToBitmap
import com.example.citassalon.presentacion.features.extensions.makeSaveAction
import com.example.citassalon.presentacion.features.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.features.extensions.showSuccessMessage
import com.example.citassalon.presentacion.features.extensions.toBase64
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
    private var colorSessionUser: MutableState<String> = mutableStateOf("")

    @SuppressLint("MutableCollectionMutableState")
    private var listUserInfo: MutableState<ArrayList<UserInfo>> = mutableStateOf(arrayListOf())

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.perfil)
    )

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
        val infoUserState = viewModel.infoUser.observeAsState()
        val imageUserState = viewModel.imageUser.observeAsState()
        val imageUserProfileState = viewModel.imageUserProfile.observeAsState()
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = imageUser?.value, contentDescription = "ImageProfile", modifier = Modifier
                    .size(144.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        launcher.launch(intent)
                    }
            )
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Nombre del usuario")
                Card(shape = RoundedCornerShape(16.dp)) {

                }
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
                    LazyColumn {
                        listUserInfo.value.forEach { userInfo ->
                            item {
                                ItemList(userInfo = userInfo)
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
                }
            }
        }
    }

    @Composable
    fun ItemList(userInfo: UserInfo) {
        Column {
            Text(text = userInfo.title)
            Text(text = userInfo.value)
        }
    }

    override fun setUpUi() {

    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK
            && result.data != null
        ) {
            val photoUri: Uri? = result.data!!.data
            Glide.with(requireContext())
                .asBitmap()
                .load(photoUri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageUser?.value = resource
                        viewModel.saveImageUser(resource.toBase64())
                    }
                })
        }
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