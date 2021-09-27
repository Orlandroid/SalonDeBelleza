package com.example.citassalon.ui.staff


import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.models.room.StaffDatabase
import com.example.citassalon.data.models.room.StaffName
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AgendarStaff : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var addButton: ImageButton

    private lateinit var staff: List<Staff>
    private var _binding: FragmentAgendarStaffBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarStaffBinding.inflate(layoutInflater, container, false)
        binding.staffBottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.recyclerStaff.setHasFixedSize(true)
        populateRecyclerView()
        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(Runnable {

            val vehicleArray = StaffDatabase
                .getInstance(context = requireContext())
                ?.StaffDao()
                ?.getStaffs() as MutableList<StaffName>

            Handler(Looper.getMainLooper()).post(Runnable {
               val adapter = AdaptadorStaff(vehicleArray, getListener())
                populateRecyclerView().adapter = adapter
            })
        })




        return binding.root

    }

    private fun populateRecyclerView() {
        staff = listOf(
            Staff(R.drawable.image_15, "Angela Bautista", 1f),
            Staff(R.drawable.image_18, "Xavier Cruz", 4f),
            Staff(R.drawable.image_19, "Flora Parra", 3f),
            Staff(R.drawable.image_20, "Jesica Estrada", 5f),
        )
        binding.recyclerStaff.adapter = AdaptadorStaff(staff, requireContext())
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