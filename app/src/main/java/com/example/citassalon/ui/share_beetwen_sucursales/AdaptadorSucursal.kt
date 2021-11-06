package com.example.citassalon.ui.share_beetwen_sucursales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.Sucursal

/** Este adpatador es usado por 2 Fragments
 *  los cuales son los siguientes
* /ui/AgendarSucursal
* /ui/info/InfoSucursal
**/

class AdaptadorSucursal(
    private val sucursales: List<Sucursal>,
    private val listener: ClickOnSucursal
) :
    RecyclerView.Adapter<AdaptadorSucursal.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartaTexto: TextView = itemView.findViewById(R.id.AStxtNombreSucursal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSucursal = sucursales[position]
        holder.cartaTexto.text = currentSucursal.nombre
        holder.itemView.setOnClickListener {
            listener.clickOnSucursal(currentSucursal)
        }
    }

    override fun getItemCount(): Int = sucursales.size


}