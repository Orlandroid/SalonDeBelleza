package com.example.citassalon.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.citassalon.R

class DetalleStaff : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var nombre: TextView
    private lateinit var evaluation: RatingBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_staff)
        initView()
        getValues()
    }

    private fun initView() {
        image = findViewById(R.id.image_view_staff)
        nombre = findViewById(R.id.d_nombre)
        evaluation = findViewById(R.id.ratingBar)
    }

    private fun getValues() {
        val imageSrc = intent.extras?.get("image") as Int
        val nameStaff = intent.extras?.get("name") as String
        val evalutionStaff = intent.extras?.get("evaluation") as Float
        setValue(imageSrc, nameStaff, evalutionStaff)
    }

    private fun setValue(imagenP: Int, nameP: String, evaluationP: Float) {
        image.setImageResource(imagenP)
        nombre.text = nameP
        evaluation.rating = evaluationP
    }

}