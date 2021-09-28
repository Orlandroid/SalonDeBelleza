package com.example.citassalon.ui.staff

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.Staff

class AdaptadorStaff(private val staff: List<Staff>) :
    RecyclerView.Adapter<AdaptadorStaff.ViewHolder>(),
    View.OnCreateContextMenuListener {

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
        val currentStaff = staff[position]
        holder.image.setImageResource(currentStaff.image)
        holder.name.text = currentStaff.name
        holder.itemView.setOnClickListener {
            val action = AgendarStaffDirections.actionAgendarStaffToDetalleStaff(currentStaff)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.setOnCreateContextMenuListener(this)

    }


    override fun getItemCount(): Int {
        return staff.size
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.add(0, 0, 0, "Añadir a favoritos")
        menu?.add(0, 1, 0, "Quitar de favoritos")
        menu?.add(0, 2, 0, "Ver Especialidades")
        menu?.add(0, 3, 0, "Añadir Reseña")
        menu?.add(0, 4, 0, "Ver Reseñas")
        menu?.add(0, 5, 0, "Reportar")
    }

}


