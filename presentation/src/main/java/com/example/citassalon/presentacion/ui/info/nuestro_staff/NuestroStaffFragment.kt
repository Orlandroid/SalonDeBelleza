package com.example.citassalon.presentacion.ui.info.nuestro_staff

import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentNuestroStaffBinding
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NuestroStaffFragment :
    BaseFragment<FragmentNuestroStaffBinding>(R.layout.fragment_nuestro_staff) {


    private val viewModel: OurStaffViewModel by viewModels()
    private var ourStaffAdapter = OurStaffAdapter()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.nuestro_staff)
    )


    override fun setUpUi() {
        binding.recycler.adapter = ourStaffAdapter
        viewModel.getStaffsUsers()

    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(viewModel.ourStaffsResponse, hasProgressTheView = true) {
            ourStaffAdapter.setData(it.users)
        }
    }


}