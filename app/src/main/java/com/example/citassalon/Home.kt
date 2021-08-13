package com.example.citassalon

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Home : Fragment() {

    private lateinit var buttonAgendar: Button
    private lateinit var buttonPerfil: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.acercade -> {
                Toast.makeText(requireContext(), "Acerca de", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.share -> {
                Toast.makeText(requireContext(), "Compartir", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.info -> {
                Toast.makeText(requireContext(), "Imformacion", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.more -> {
                Toast.makeText(requireContext(), "Mas", Toast.LENGTH_LONG).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        buttonAgendar = view.findViewById(R.id.button_agendar)
        buttonPerfil = view.findViewById(R.id.btnFloatingPerfil)
        buttonAgendar.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_agendarSucursal)
        }
        buttonPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_perfil)
        }
        return view
    }

}