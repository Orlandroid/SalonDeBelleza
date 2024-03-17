package com.example.citassalon.presentacion.features.info.ubicacion

import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentUbicacionBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.domain.entities.remote.migration.NegoInfo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson

class UbicacionFragment : BaseFragment<FragmentUbicacionBinding>(R.layout.fragment_ubicacion),
    OnMapReadyCallback {

    private val args: UbicacionFragmentArgs by navArgs()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.ubicacion)
    )


    override fun setUpUi() {
        with(binding) {
            val sucursal = Gson().fromJson(args.currentSucursal, NegoInfo::class.java)
            textView10.text = sucursal.sucursal.name
            initMap()
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(-37.3159, 81.1496)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 5.5f))
    }

}