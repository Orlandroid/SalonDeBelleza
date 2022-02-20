package com.example.citassalon.ui.staff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.Staff
import com.example.citassalon.util.navigate

class AdaptadorStaff(private val listener: ClickOnStaff) :
    RecyclerView.Adapter<AdaptadorStaff.ViewHolder>() {

    private var listaStaff: List<Staff> = arrayListOf()

    fun setData(lista: List<Staff>) {
        listaStaff = lista
        notifyDataSetChanged()
    }

    fun getData(): List<Staff> = listaStaff

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.staff_picture)
        val name: TextView = itemView.findViewById(R.id.staff_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_staff, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentStaff = listaStaff[position]
        holder.image.setImageResource(currentStaff.getResourceImage())
        holder.name.text = currentStaff.nombre
        holder.itemView.setOnClickListener {
            val action = AgendarStaffDirections.actionAgendarStaffToDetalleStaff(currentStaff)
            it.navigate(action)
        }
        holder.image.setOnClickListener {
            listener.clickOnStaff(currentStaff)
        }
    }

    override fun getItemCount(): Int {
        return listaStaff.size
    }

}


