package com.example.citassalon.ui.staff


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.data.models.Staff
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaff : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {


    private lateinit var staff: List<Staff>
    private var _binding: FragmentAgendarStaffBinding? = null
    private val binding get() = _binding!!
    private val viewModelStaff: ViewModelStaff by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarStaffBinding.inflate(layoutInflater, container, false)
        binding.staffBottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.recyclerStaff.setHasFixedSize(true)
        setUpObservers()
        return binding.root

    }

    private fun setUpObservers() {
        viewModelStaff.staffs.observe(viewLifecycleOwner, {
            binding.recyclerStaff.adapter = AdaptadorStaff(it)
        })
    }

    private fun populateRecyclerView() {
        staff = listOf(
            Staff(0, R.drawable.image_15, "Angela Bautista", 1f),
            Staff(0, R.drawable.image_18, "Xavier Cruz", 4f),
            Staff(0, R.drawable.image_19, "Flora Parra", 3f),
            Staff(0, R.drawable.image_20, "Jesica Estrada", 5f),
        )
        binding.recyclerStaff.adapter = AdaptadorStaff(staff)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                findNavController().navigate(R.id.action_agendarStaff_to_agendarSucursal)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarStaff_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarStaff_to_agendarServicio)
                true
            }
            else -> false
        }
    }


}