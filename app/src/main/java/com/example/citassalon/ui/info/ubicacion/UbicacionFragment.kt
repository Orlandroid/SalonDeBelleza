package com.example.citassalon.ui.info.ubicacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUbicacionBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class UbicacionFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentUbicacionBinding? = null
    private val binding get() = _binding!!
    private val args:UbicacionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUbicacionBinding.inflate(layoutInflater, container, false)
        setUpUi()
        initMap()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Ubicacion"
            textView10.text=args.currentSucursal.name
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(args.currentSucursal.lat.toDouble(), args.currentSucursal.long.toDouble())
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,5.5f))
    }

}