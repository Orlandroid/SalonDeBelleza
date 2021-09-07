package com.example.citassalon.fragments


import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.adapters.AdaptadorAgendarStaff
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.models.Staff
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarStaff : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener,
    SensorEventListener {


    private lateinit var staff: List<Staff>
    private var _binding: FragmentAgendarStaffBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarStaffBinding.inflate(layoutInflater, container, false)
        binding.staffBottomNavigationView.setOnNavigationItemSelectedListener(this)
        setUpSensorStuff()
        binding.recyclerStaff.setHasFixedSize(true)
        populateRecyclerView()
        return binding.root
    }

    private fun populateRecyclerView() {
        staff = listOf(
            Staff(R.drawable.image_15, "Angela Bautista", 1f),
            Staff(R.drawable.image_18, "Xavier Cruz", 4f),
            Staff(R.drawable.image_19, "Flora Parra", 3f),
            Staff(R.drawable.image_20, "Jesica Estrada", 5f),
        )
        binding.recyclerStaff.adapter = AdaptadorAgendarStaff(staff, requireContext())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                findNavController().navigate(R.id.action_agendarStaff_to_agendarSucursal)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarStaff_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarStaff_to_agendarServicio)
                true
            }
            else -> false
        }
    }

    private fun setUpSensorStuff() {
        // Create the sensor manager
        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager

        // Specify the sensor you want to listen to
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            Toast.makeText(requireContext(), event.sensor.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

}