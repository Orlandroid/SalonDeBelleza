package com.example.citassalon

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AgendarFecha : Fragment() {

    private lateinit var buttonNext: ImageButton
    private lateinit var eTDate: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_fecha, container, false)
        buttonNext = view.findViewById(R.id.button_next)
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_agendarFecha_to_agendarHora)
        }
        eTDate = view.findViewById(R.id.select_Date)
        eTDate.setOnClickListener{
            showDatePickerDialog()
        }
        return view

    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            eTDate.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }


}