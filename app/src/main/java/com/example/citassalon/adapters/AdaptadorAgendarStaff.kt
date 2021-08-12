package com.example.citassalon.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.AgendarServicio
import com.example.citassalon.DetalleStaff
import com.example.citassalon.R
import com.example.citassalon.models.Staff

class AdaptadorAgendarStaff(private val staff: List<Staff>, private val context: Context) :
    RecyclerView.Adapter<AdaptadorAgendarStaff.ViewHolder>(),
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
        val item = staff[position]
        holder.image.setImageResource(item.image)
        holder.name.text = item.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalleStaff::class.java)
            intent.putExtra("image", staff[position].image)
            intent.putExtra("name", staff[position].name)
            intent.putExtra("evaluation", staff[position].evaluation)
            context.startActivity(intent)
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


