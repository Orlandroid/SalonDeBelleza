package com.example.citassalon.presentacion.features.perfil.userprofile


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUserProfileBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.base64StringToBitmap
import com.example.citassalon.presentacion.features.extensions.click
import com.example.citassalon.presentacion.features.extensions.makeSaveAction
import com.example.citassalon.presentacion.features.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.features.extensions.showSuccessMessage
import com.example.citassalon.presentacion.features.extensions.toBase64
import com.example.citassalon.presentacion.util.parseColor
import com.example.data.preferences.LoginPreferences
import com.example.domain.perfil.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(R.layout.fragment_user_profile) {

    @Inject
    lateinit var loginPeferences: LoginPreferences

    private val viewModel: UserProfileViewModel by viewModels()
    private var listOfUserInfo: ArrayList<UserInfo> = arrayListOf()
    private val adapter = UserProfileAdapter()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.perfil)
    )

    companion object {
        const val USER_EMAIL = "email"
        const val USER_UID = "uid"
        const val USER_SESSION = "userSession"
    }

    override fun setUpUi() = with(binding) {
        viewModel.getUserInfo()
        viewModel.getUserImage()
        imageUser.click {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(intent)
        }
        recycler.adapter = adapter
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
                        binding.imageUser.setImageResource(R.drawable.ic_baseline_broken_image_24)
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.imageUser.setImageBitmap(resource)
                        viewModel.saveImageUser(resource.toBase64())
                    }
                })
        }
    }

    private fun setListUserInfo() {
        listOfUserInfo.add(UserInfo("Nombre"))
        listOfUserInfo.add(UserInfo("Telefono"))
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.infoUser,
            shouldCloseTheViewOnApiError = true,
            hasProgressTheView = true,
            onLoading = { binding.skeletonInfo.showSkeleton() },
            onFinishLoading = { binding.skeletonInfo.showOriginal() }
        ) {
            with(binding) {
                setListUserInfo()
                listOfUserInfo.add(UserInfo("correo", it[USER_EMAIL] ?: ""))
                listOfUserInfo.add(UserInfo("uid", it[USER_UID] ?: ""))
                listOfUserInfo.add(
                    UserInfo(
                        "Money",
                        "$ " + loginPeferences.getUserMoney().toString()
                    )
                )
                adapter.setData(listOfUserInfo)
                if (it[USER_SESSION].equals("true")) {
                    imageStatusSession.setColorFilter(parseColor("#239b56"))//verde
                } else {
                    imageStatusSession.setColorFilter(parseColor("#aab7b8"))//gris
                }
            }
        }
        observeApiResultGeneric(liveData = viewModel.imageUser) {
            showSuccessMessage()
        }
        observeApiResultGeneric(
            liveData = viewModel.imageUserProfile,
            shouldCloseTheViewOnApiError = true,
            hasProgressTheView = false
        ) {
            it.makeSaveAction {
                Glide.with(requireContext()).load(it!!.base64StringToBitmap())
                    .placeholder(R.drawable.loading_animation)
                    .transition(DrawableTransitionOptions.withCrossFade()).circleCrop()
                    .into(binding.imageUser)
            }
        }
    }

}