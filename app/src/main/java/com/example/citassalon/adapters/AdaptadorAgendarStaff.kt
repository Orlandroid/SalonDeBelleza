package com.example.citassalon.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.AgendarServicio
import com.example.citassalon.R
import com.example.citassalon.models.Staff

class AdaptadorAgendarStaff(private val staff: List<Staff>) :
    RecyclerView.Adapter<AdaptadorAgendarStaff.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.staff_picture)
        val name: TextView = itemView.findViewById(R.id.staff_name)

        init {
            itemView.setOnClickListener {
                Log.w("CLIKC", "Click en el elemento")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_staff, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = staff[position]
        holder.image.setImageResource(item.image)
        holder.name.text = item.name
    }


    override fun getItemCount(): Int {
        return staff.size
    }

}


