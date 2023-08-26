package com.example.citassalon.presentacion.ui.fecha

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimePickerFragment(
    val listener: (String, Boolean) -> Unit,
    private val is24HoursView: Boolean = false
) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(activity as Context, this, hour, minute, is24HoursView)
        return timePicker
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        var isValidTime = false
        if (hourOfDay in 9..18) {
            isValidTime = true
        }
        listener("$hourOfDay:$minute hrs", isValidTime)
    }

}