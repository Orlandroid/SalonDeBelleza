package com.example.citassalon.ui.staff


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.Staff

class AdaptadorStaff(private val staff: List<Staff>, private val listener: ClickOnStaff) :
    RecyclerView.Adapter<AdaptadorStaff.ViewHolder>() {

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
        holder.image.setImageResource(currentStaff.getResourceImage())
        holder.name.text = currentStaff.nombre
        holder.itemView.setOnClickListener {
            val action = AgendarStaffDirections.actionAgendarStaffToDetalleStaff(currentStaff)
            holder.itemView.findNavController().navigate(action)
        }
        holder.image.setOnClickListener {
            listener.clickOnStaf(currentStaff)
        }
    }

    override fun getItemCount(): Int {
        return staff.size
    }

}


