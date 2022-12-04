package com.example.citassalon.ui.fecha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarFechaBinding
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.main.AlertDialogs.Companion.WARNING_MESSAGE
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.hideKeyboard
import com.example.citassalon.ui.extensions.navigate
import com.example.citassalon.ui.extensions.showDatePickerDialog
import com.example.citassalon.ui.flow_main.FlowMainViewModel

class AgendarFechaFragment :
    BaseFragment<FragmentAgendarFechaBinding>(R.layout.fragment_agendar_fecha),
    DatePickerDialog.OnDateSetListener {

    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = "Agendar Hora"
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
        setValuesToView()

    }

    private fun setValuesToView() {
        with(binding) {
            flowMainViewModel.currentStaff.let {
                Glide.with(requireActivity()).load(it.image_url).into(imgStaff)
                tvStaffName.text = it.nombre
            }
            flowMainViewModel.let {
                textSucursal.text = it.sucursal.name
                txtServicio.text = it.currentService.name
                tvServicioPrecio.text = it.currentService.precio.toString()
            }
        }
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
        val timePicker = TimePickerFragment({ time, validTime ->
            if (validTime) {
                onTimeSelected(time)
                goToComfirm()
                return@TimePickerFragment
            }
            showDialogRangeOfTime()
        })
        activity?.let { timePicker.show(it.supportFragmentManager, "timePicker") }
    }

    private fun showDialogRangeOfTime() {
        val alert = AlertDialogs(
            kindOfMessage = WARNING_MESSAGE,
            messageBody = "Debes de selecionar un horario entre 9am y 6pm"
        )
        activity?.let { alert.show(it.supportFragmentManager, "Dialog") }
    }

    private fun onTimeSelected(time: String) {
        binding.edHora.editText?.setText(time)
    }


    private fun goToComfirm() {
        if (areNotEmptyTimeOrDate()) {
            val action = AgendarFechaFragmentDirections.actionAgendarFechaToAgendarConfirmacion()
            navigate(action)
        }
    }

    private fun areNotEmptyTimeOrDate(): Boolean {
        val date = binding.etFecha.editText?.text.toString().trim()
        val time = binding.edHora.editText?.text.toString().trim()
        return date.isNotEmpty() and time.isNotEmpty()
    }

}