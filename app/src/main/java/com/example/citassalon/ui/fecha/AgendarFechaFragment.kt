package com.example.citassalon.ui.fecha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.databinding.FragmentAgendarFechaBinding
import com.example.citassalon.util.hideKeyboard
import com.example.citassalon.util.navigate
import com.example.citassalon.util.showDatePickerDialog

class AgendarFechaFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentAgendarFechaBinding? = null
    private val binding get() = _binding!!

    private val args: AgendarFechaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarFechaBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root

    }

    private fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text="Agendar Hora"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            edHora.setEndIconOnClickListener {
                showTimePickerDialog()
            }
            mainView.setOnClickListener {
                hideKeyboard()
            }
            etFecha.setEndIconOnClickListener {
                showDatePickerDialog(getListenerOnDataSet(), this@AgendarFechaFragment, true)
            }
        }
        setValuesToView(args)

    }

    private fun setValuesToView(args: AgendarFechaFragmentArgs) {
        binding.imgStaff.setImageResource(args.staff.getResourceImage())
        binding.tvStaffName.text = args.staff.nombre
        binding.txtServicio.text = args.servicio.name
        binding.tvServicioPrecio.text = args.servicio.precio.toString()
        binding.textSucursal.text = args.sucursal
    }


    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
        binding.etFecha.editText?.setText(selectedDate)
        goToComfirm()
    }

    private fun getListenerOnDataSet(): DatePickerDialog.OnDateSetListener {
        return this
    }


    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment {
            onTimeSelected(it)
            goToComfirm()
        }
        activity?.let { timePicker.show(it.supportFragmentManager, "timePicker") }
    }

    private fun onTimeSelected(time: String) {
        binding.edHora.editText?.setText(time)
    }


    private fun goToComfirm() {
        if (areNotEmptyTimeOrDate()) {
            val action = AgendarFechaFragmentDirections.actionAgendarFechaToAgendarConfirmacion(
                args.sucursal,
                args.staff,
                args.servicio,
                binding.etFecha.editText?.text.toString(),
                binding.edHora.editText?.text.toString()
            )
            navigate(action)
        }
    }


    private fun areNotEmptyTimeOrDate(): Boolean {
        val date = binding.etFecha.editText?.text.toString().trim()
        val time = binding.edHora.editText?.text.toString().trim()
        return date.isNotEmpty() and time.isNotEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}