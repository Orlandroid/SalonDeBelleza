package com.example.citassalon.presentacion.ui.perfil.userprofile


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUserProfileBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.*
import com.example.citassalon.presentacion.util.parseColor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModels()

    companion object {
        const val USER_EMAIL = "email"
        const val USER_UID = "uid"
        const val USER_SESSION = "userSession"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() = with(binding) {
        viewModel.getUserInfo()
        viewModel.getUserImage()
        toolbarLayout.toolbarTitle.text = getString(R.string.perfil)
        toolbarLayout.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        imageUser.click {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(intent)
        }
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

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.infoUser,
            shouldCloseTheViewOnApiError = true,
            hasProgressTheView = false,
            onLoading = { binding.skeletonInfo.showSkeleton() },
            onFinishLoading = { binding.skeletonInfo.showOriginal() }
        ) {
            with(binding) {
                tvCorreo.text = it[USER_EMAIL]
                tvUid.text = it[USER_UID]
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