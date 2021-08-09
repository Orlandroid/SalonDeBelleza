package com.example.citassalon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class Perfil : Fragment() {

    private lateinit var imageButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        imageButton = view.findViewById(R.id.p_button_home)
        imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_perfil_to_home3)
        }
        return view
    }
}