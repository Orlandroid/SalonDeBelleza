package com.example.citassalon


import android.os.Bundle
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.core.view.GravityCompat.apply
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.adapters.AdaptadorAgendarStaff
import com.example.citassalon.models.Staff
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarStaff : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var staffRecyclerView: RecyclerView
    private lateinit var staff: List<Staff>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_staff, container, false)
        staffRecyclerView = view.findViewById(R.id.recyclerStaff)
        bottomNavigationView = view.findViewById(R.id.staff_bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
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
        staffRecyclerView.layoutManager = GridLayoutManager(context, 2)
        staffRecyclerView.setHasFixedSize(true)
        populateRecyclerView()
        return view
    }

    private fun populateRecyclerView() {
        staff = listOf(
            Staff(R.drawable.image_15, "Angela Bautista", 1f),
            Staff(R.drawable.image_18, "Xavier Cruz", 4f),
            Staff(R.drawable.image_19, "Flora Parra", 3f),
            Staff(R.drawable.image_20, "Jesica Estrada", 5f),
        )
        staffRecyclerView.adapter = AdaptadorAgendarStaff(staff, requireContext())
    }

}