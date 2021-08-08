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

class AgendarStaff : Fragment() {

    private lateinit var buttonSigiente: Button
    private lateinit var staffRecyclerView: RecyclerView
    private lateinit var staff: List<Staff>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_staff, container, false)
        buttonSigiente = view.findViewById(R.id.button_next_servicio)
        staffRecyclerView = view.findViewById(R.id.recyclerStaff)
        staffRecyclerView.layoutManager = GridLayoutManager(context, 2)
        staffRecyclerView.setHasFixedSize(true)
        populateRecyclerView()
        buttonSigiente.setOnClickListener {
            findNavController().navigate(R.id.action_agendarStaff_to_agendarServicio)


        }
        return view
    }

    private fun populateRecyclerView() {
        staff = listOf(
            Staff(R.drawable.image_15, "Angela Bautista"),
            Staff(R.drawable.image_18, "Xavier Cruz"),
            Staff(R.drawable.image_19, "Flora Parra"),
            Staff(R.drawable.image_20, "Jesica Estrada"),
        )
        staffRecyclerView.adapter = AdaptadorAgendarStaff(staff)
    }

}