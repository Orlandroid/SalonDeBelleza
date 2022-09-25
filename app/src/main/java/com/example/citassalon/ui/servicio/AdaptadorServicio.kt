package com.example.citassalon.ui.servicio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.interfaces.ClickOnItem

class AdaptadorServicio(
    private val servicios: List<Servicio>,
    private val listener: ClickOnItem<Servicio>
) :
    RecyclerView.Adapter<AdaptadorServicio.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val servicio: TextView = itemView.findViewById(R.id.NombreServicio)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_servicio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = servicios[position]
        holder.servicio.text = item.name
        holder.itemView.setOnClickListener {
            listener.clikOnElement(item)
        }

    }

    override fun getItemCount(): Int = servicios.size


}