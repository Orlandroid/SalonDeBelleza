package com.example.citassalon.ui.perfil.userprofile


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentUserProfileBinding
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.invisible
import com.example.citassalon.ui.extensions.visible
import com.example.citassalon.util.parseColor
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
            toolbarLayout.toolbarTitle.text = "Perfil"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.infoUser.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {
                    binding.progressBar.visible()
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        with(binding) {
                            progressBar.invisible()
                            tvCorreo.text = it.data[USER_EMAIL]
                            tvUid.text = it.data[USER_UID]
                            if (it.data[USER_SESSION].equals("true")) {
                                imageStatusSession.setColorFilter(parseColor("#239b56"))//verde
                            } else {
                                imageStatusSession.setColorFilter(parseColor("#aab7b8"))//gris
                            }
                        }
                    }
                }
                is ApiState.Error -> {

                }
                is ApiState.ErrorNetwork -> {

                }
                is ApiState.NoData -> {

                }
            }
        }
    }


}