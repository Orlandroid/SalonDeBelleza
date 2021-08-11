package com.example.citassalon

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class DatePicker : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_agendar_fecha)
        lateinit var dateSelect: EditText
        dateSelect = findViewById(R.id.select_Date)
        dateSelect.setOnClickListener {showDatePickerDialog()}
    }

    fun onDateSelected(day:Int, month:Int, year:Int ) {
    }
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datepicker")


    }
}