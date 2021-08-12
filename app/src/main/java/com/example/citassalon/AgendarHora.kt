package com.example.citassalon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarHora : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_hora, container, false)
        bottomNavigationView=view.findViewById(R.id.botton_navigation_view_agendar_hora)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_back -> {
                    findNavController().navigate(R.id.action_agendarHora_to_agendarFecha)
                    true
                }
                R.id.item_home -> {
                    findNavController().navigate(R.id.action_agendarHora_to_home3)
                    true
                }
                R.id.item_next -> {
                    findNavController().navigate(R.id.action_agendarHora_to_citaAgendada)
                    true
                }
                else -> false
            }
        }
        return view
    }


}