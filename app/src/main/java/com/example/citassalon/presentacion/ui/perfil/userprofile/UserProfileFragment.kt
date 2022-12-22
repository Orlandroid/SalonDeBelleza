package com.example.citassalon.presentacion.ui.perfil.userprofile


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUserProfileBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
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

    override fun setUpUi() {
        viewModel.getUserInfo()
        with(binding) {
            toolbarLayout.toolbarTitle.text = getString(R.string.perfil)
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            liveData = viewModel.infoUser,
            shouldCloseTheViewOnApiError = true,
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
    }


}