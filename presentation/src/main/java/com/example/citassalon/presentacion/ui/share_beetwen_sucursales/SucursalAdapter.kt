package com.example.citassalon.presentacion.ui.share_beetwen_sucursales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.domain.entities.remote.migration.NegoInfo


class SucursalAdapter(
    private val sucursales: List<NegoInfo>,
    private val listener: ClickOnItem<NegoInfo>
) :
    RecyclerView.Adapter<SucursalAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartaTexto: TextView = itemView.findViewById(R.id.AStxtNombreSucursal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val negocioInfo = sucursales[position]
        holder.cartaTexto.text = negocioInfo.sucursal.name
        holder.itemView.setOnClickListener {
            listener.clikOnElement(negocioInfo)
        }
    }

    override fun getItemCount(): Int = sucursales.size


}