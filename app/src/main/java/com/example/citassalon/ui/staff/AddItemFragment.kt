package com.example.citassalon.ui.staff

import android.content.ClipData
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.data.models.room.*
import com.example.citassalon.databinding.FragmentAddEditStaffBinding
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddItemFragment  : Fragment() {
    private lateinit var viewModel: AddEditViewModel
    private lateinit var binding: FragmentAddEditStaffBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_edit_staff,
            container,
            false
        )

        viewModel = AddEditViewModel(
            (requireContext().applicationContext as StaffApp).staffRepository
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.buttonAddStaff.setOnClickListener {
            viewModel.newStaff()
        }

        setupNavigation()

        return binding.root
    }

    fun setupNavigation() {
        viewModel.staffDone.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(
                    R.id.action_addEditFragment_to_staffListFragment
                )
            }
        })
    }
}

