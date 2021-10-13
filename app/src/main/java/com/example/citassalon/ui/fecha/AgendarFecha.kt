package com.example.citassalon.ui.fecha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarFechaBinding
import com.example.citassalon.util.AlertsDialogMessages
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarFecha : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentAgendarFechaBinding? = null
    private val binding get() = _binding!!

    private val args: AgendarFechaArgs by navArgs()

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

        binding.edHora.setOnClickListener {
            showTimePickerDialog()
        }
        setValuesToView(args)

        return binding.root

    }

    private fun setValuesToView(args: AgendarFechaArgs) {
        binding.imgStaff.setImageResource(args.staff.getResourceImage())
        binding.tvStaffName.text = args.staff.nombre
        binding.txtServicio.text = args.servicio.name
        binding.tvServicioPrecio.text = args.servicio.precio.toString()
        binding.textSucursal.text = args.sucursal
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.selectDate.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        activity?.let { timePicker.show(it.supportFragmentManager, "timePicker") }
    }

    private fun onTimeSelected(time: String) {
        binding.edHora.setText(time)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarFecha_to_home3)
                true
            }
            R.id.item_next -> {
                val action = AgendarFechaDirections.actionAgendarFechaToAgendarConfirmacion(
                    args.sucursal,
                    args.staff,
                    args.servicio,
                    binding.selectDate.text.toString(),
                    binding.edHora.text.toString()
                )
                if (areEmptyTimeOrDate()) {
                    findNavController().navigate(action)
                } else {
                    notMove()
                }
                true
            }
            else -> false
        }
    }

    private fun areEmptyTimeOrDate(): Boolean {
        val date = binding.selectDate.text.toString().trim()
        val time = binding.edHora.text.toString().trim()
        return date.isNotEmpty() and time.isNotEmpty()
    }

    private fun notMove() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showSimpleMessage(
            requireContext().getString(R.string.informacion),
            requireContext().getString(R.string.selecciona_hora_fecha)
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}