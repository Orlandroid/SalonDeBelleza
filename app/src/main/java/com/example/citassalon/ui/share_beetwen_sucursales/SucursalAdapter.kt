package com.example.citassalon.ui.share_beetwen_sucursales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.migration.NegoInfo
import com.example.citassalon.data.models.remote.migration.Sucursal
import com.example.citassalon.interfaces.ClickOnItem


class SucursalAdapter(
    private val sucursales: List<NegoInfo>,
    private val listener: ClickOnItem<Sucursal>
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
        val currentSucursal = sucursales[position]
        holder.cartaTexto.text = currentSucursal.sucursal.name
        holder.itemView.setOnClickListener {
            listener.clikOnElement(currentSucursal.sucursal)
        }
    }

    override fun getItemCount(): Int = sucursales.size


}