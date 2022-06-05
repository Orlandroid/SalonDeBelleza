package com.example.citassalon.ui.perfil.userprofile

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentUserProfileBinding
import com.example.citassalon.util.parseColor
import com.example.citassalon.util.setColorFilterImage
import com.example.citassalon.util.tint
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserProfileViewModel by viewModels()

    companion object {
        const val USER_EMAIL = "email"
        const val USER_UID = "uid"
        const val USER_SESSION = "userSession"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        setUpUi()
        setUpObservers()
        return binding.root
    }

    private fun setUpUi() {
        viewModel.getUserInfo()
        with(binding) {

        }
    }

    private fun setUpObservers() {
        viewModel.infoUser.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        with(binding) {
                            progressBar.visibility = View.INVISIBLE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}