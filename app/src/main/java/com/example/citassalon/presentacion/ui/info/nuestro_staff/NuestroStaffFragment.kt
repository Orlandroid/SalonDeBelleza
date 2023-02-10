package com.example.citassalon.presentacion.ui.info.nuestro_staff

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentNuestroStaffBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.ui.extensions.packageName
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NuestroStaffFragment :
    BaseFragment<FragmentNuestroStaffBinding>(R.layout.fragment_nuestro_staff) {


    private val viewModel: OurStaffViewModel by viewModels()
    private var ourStaffAdapter = OurStaffAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = getString(R.string.nuestro_staff)
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            binding.recycler.adapter = ourStaffAdapter
            viewModel.getStaffsUsers()
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(viewModel.ourStaffsResponse, hasProgressTheView = true) {
            ourStaffAdapter.setData(it.users)
            Log.w(packageName(), "Response dummy Users")
        }
    }


}