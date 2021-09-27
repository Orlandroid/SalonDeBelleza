package com.example.citassalon.ui.staff.stafflist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.room.StaffApp
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.ui.staff.AdaptadorStaff

class StaffListFragment : Fragment(){


    private lateinit var adapter: AdaptadorStaff
    private lateinit var viewModel: StaffListViewModel
    private lateinit var binding: FragmentAgendarStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_agendar_staff,
            container,
            false
        )

        viewModel = StaffListViewModel(
            (requireContext().applicationContext as StaffApp).staffRepository
        )

        binding.lifecycleOwner = this
        binding.StaffListViewModel = viewModel

        setupEditVehicle()


        binding.buttonAddStaff.setOnClickListener {
            findNavController().navigate(
                R.id.action_StaffListFragment_to_addEditFragment
            )
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupVehicleList()

    }

    private fun setupVehicleList(){
        if(viewModel!=null){
            adapter = AdaptadorStaff(viewModel)
            binding.list.adapter = adapter

            viewModel.staffList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })

        }
    }

    private fun setupEditVehicle(){
        if(viewModel!=null){

            with(viewModel){

                eventEditVehicle.observe(viewLifecycleOwner, Observer {
                    if(eventEditVehicle.value!=null){
                        findNavController().navigate(
                            R.id.action_vehicleListFragment_to_addEditFragment,
                            bundleOf("vehicle_id" to eventEditVehicle.value!!)
                        )
                        eventEditVehicle.value = null
                    }
                })
            }
        }

    }



}