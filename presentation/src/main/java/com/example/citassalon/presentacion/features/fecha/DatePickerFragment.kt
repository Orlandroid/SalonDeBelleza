package com.example.citassalon.presentacion.features.fecha

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment(
    private val contextP: Context,
    private val listener: DatePickerDialog.OnDateSetListener,
    private val setMinDate: Boolean = false
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val picker = DatePickerDialog(contextP, listener, year, month, day)
        val today = c.timeInMillis
        if (setMinDate){
            picker.datePicker.minDate = today
        }
        return picker
    }

    companion object {
        fun newInstance(
            listener: DatePickerDialog.OnDateSetListener,
            contextP: Context,
            setMinDate:Boolean=false
        ): DatePickerFragment {
            return DatePickerFragment(contextP, listener,setMinDate)
        }
    }

}