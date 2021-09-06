package com.example.citassalon.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.adapters.AdaptadorAgendarSucursal
import com.example.citassalon.data.retrofit.RetrofitInstance
import com.example.citassalon.data.retrofit.WebServices
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.models.Sucursal
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AgendarSucursal : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {


    private var _binding: FragmentAgendarSucursalBinding? = null
    private val binding get() = _binding!!
    val retrofit = RetrofitInstance.getRetrofit()
    val webServices = retrofit.create(WebServices::class.java)
    val call = webServices.getPokemon()


    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarSucursalBinding.inflate(inflater)
        binding.sucursalBottomNavigationView.setOnNavigationItemSelectedListener(this)
        makeRequest()
        return binding.root
    }


    private fun makeRequest() {
        call.enqueue(object : Callback<List<Sucursal>> {

            override fun onFailure(call: Call<List<Sucursal>>, t: Throwable) {
                Log.e("error", "Error: $t")
            }

            override fun onResponse(
                call: Call<List<Sucursal>>,
                response: Response<List<Sucursal>>
            ) {
                if (response.code() == 200) {
                    Log.e("Respuesta", "${response.body()}")
                    val country = response.body()
                    binding.recyclerSucursal.adapter =
                        AdaptadorAgendarSucursal(country!!, binding.textAgendarSucursal)
                } else {
                    Log.e("Not200", "Error not 200: $response")
                }
            }

        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_agendarStaff)
                true
            }
            else -> false
        }
    }

}