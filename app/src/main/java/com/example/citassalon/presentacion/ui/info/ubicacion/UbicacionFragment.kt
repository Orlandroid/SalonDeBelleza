package com.example.citassalon.presentacion.ui.info.ubicacion

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUbicacionBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbicacionFragment : BaseFragment<FragmentUbicacionBinding>(R.layout.fragment_ubicacion),
    OnMapReadyCallback {

    private val args: UbicacionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        initMap()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Ubicacion"
            textView10.text = args.currentSucursal.name
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney =
            LatLng(args.currentSucursal.lat.toDouble(), args.currentSucursal.long.toDouble())
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 5.5f))
    }

}