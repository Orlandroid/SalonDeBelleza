package com.example.citassalon.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentHomeBinding


class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.buttonAgendar.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_agendarSucursal)
        }
        binding.btnFloatingPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_perfil)
        }
        return binding.root
    }

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


}