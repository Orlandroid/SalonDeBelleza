package com.example.citassalon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.models.Sucursal

class AdaptadorAgendarSucursal(
    private val sucursales: List<Sucursal>,
    private val texto: TextView
) :
    RecyclerView.Adapter<AdaptadorAgendarSucursal.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartaTexto: TextView = itemView.findViewById(R.id.AStxtNombreSucursal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cartaTexto.text = sucursales[position].name
        holder.itemView.setOnClickListener {
            texto.text = sucursales[position].name
        }
    }

    override fun getItemCount(): Int = sucursales.size


}