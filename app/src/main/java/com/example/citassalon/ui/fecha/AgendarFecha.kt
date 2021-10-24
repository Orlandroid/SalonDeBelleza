package com.example.citassalon.ui.fecha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.citassalon.databinding.FragmentAgendarFechaBinding
import com.example.citassalon.util.navigate

class AgendarFecha : Fragment() {

    private var _binding: FragmentAgendarFechaBinding? = null
    private val binding get() = _binding!!

    private val args: AgendarFechaArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarFechaBinding.inflate(inflater, container, false)
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
                goToComfirm()
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment {
            onTimeSelected(it)
            goToComfirm()
        }
        activity?.let { timePicker.show(it.supportFragmentManager, "timePicker") }
    }

    private fun onTimeSelected(time: String) {
        binding.edHora.setText(time)
    }


    private fun goToComfirm() {
        if (areNotEmptyTimeOrDate()) {
            val action = AgendarFechaDirections.actionAgendarFechaToAgendarConfirmacion(
                args.sucursal,
                args.staff,
                args.servicio,
                binding.selectDate.text.toString(),
                binding.edHora.text.toString()
            )
            navigate(action)
        }
    }


    private fun areNotEmptyTimeOrDate(): Boolean {
        val date = binding.selectDate.text.toString().trim()
        val time = binding.edHora.text.toString().trim()
        return date.isNotEmpty() and time.isNotEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}