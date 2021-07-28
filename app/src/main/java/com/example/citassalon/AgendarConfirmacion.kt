package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class AgendarConfirmacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_confirmacion)
        Toast.makeText(this, "Entrando a la cuarta actividad", Toast.LENGTH_SHORT).show()
    }

}