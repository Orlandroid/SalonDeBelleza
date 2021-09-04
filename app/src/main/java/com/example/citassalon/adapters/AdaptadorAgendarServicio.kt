package com.example.citassalon.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.models.Servicio

class AdaptadorAgendarServicio(private val servicios: List<Servicio>) :
    RecyclerView.Adapter<AdaptadorAgendarServicio.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val servicio: TextView = itemView.findViewById(R.id.NombreServicio)

        init {
            itemView.setOnClickListener {
                Log.w("CLIKC", "Click en el elemento")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_servicio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = servicios[position]
        holder.servicio.text = item.servicio

    }

    override fun getItemCount(): Int = servicios.size


}