package com.example.citassalon.ui.fecha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarFechaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarFecha : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentAgendarFechaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarFechaBinding.inflate(inflater, container, false)
        binding.bottonNavigationViewAgendarFecha.setOnNavigationItemSelectedListener(this)
        binding.selectDate.setOnClickListener {
            showDatePickerDialog()
        }

        return binding.root

    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.selectDate.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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


}