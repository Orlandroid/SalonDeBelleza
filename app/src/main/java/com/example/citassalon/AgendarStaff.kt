package com.example.citassalon


import android.os.Bundle
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.core.view.GravityCompat.apply
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.adapters.AdaptadorAgendarStaff
import com.example.citassalon.models.Staff

class AgendarStaff : Fragment() {

    private lateinit var buttonSigiente: Button
    private lateinit var staffRecyclerView: RecyclerView
    private lateinit var staffArrayList: ArrayList<Staff>

    private lateinit var imageId:Array<Int>
    private lateinit var name:Array<String>
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<AdaptadorAgendarStaff.ViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_staff, container, false)
        buttonSigiente = view.findViewById(R.id.button_next_servicio)
        imageId = arrayOf(R.drawable.image_15, R.drawable.image_18, R.drawable.image_19, R.drawable.image_20)
        name = arrayOf("Angela Bautista","Xavier Cruz", "Flora Parra", "Jessica Estrada")

        staffRecyclerView = view.findViewById(R.id.recyclerStaff)
        staffRecyclerView.layoutManager = LinearLayoutManager(context)
        staffRecyclerView.setHasFixedSize(true)
        staffArrayList = arrayListOf()
        getUserdata()
        buttonSigiente.setOnClickListener {
            findNavController().navigate(R.id.action_agendarStaff_to_agendarServicio)


        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun getUserdata() {
        for(i in imageId.indices){

            val staff= Staff(imageId[i], name[i])
            staffArrayList.add(staff)
        }

        staffRecyclerView.adapter = AdaptadorAgendarStaff(staffArrayList)



    }

}