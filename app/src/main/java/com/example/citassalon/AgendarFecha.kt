package com.example.citassalon

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class AgendarFecha : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_fecha, container, false)
        bottomNavigationView=view.findViewById(R.id.botton_navigation_view_agendar_fecha)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_back -> {
                    findNavController().navigate(R.id.action_agendarFecha_to_agendarServicio)
                    true
                }
                R.id.item_home -> {
                    findNavController().navigate(R.id.action_agendarFecha_to_home3)
                    true
                }
                R.id.item_next -> {
                    findNavController().navigate(R.id.action_agendarFecha_to_agendarHora)
                    true
                }
                else -> false
            }
        }
        return view
    }


}