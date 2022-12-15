package com.example.citassalon.ui.servicio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.migration.Service
import com.example.citassalon.databinding.ItemServicioBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.ui.extensions.getColor

class AgendarServicioAdapter(
    private var servicios: List<Service>,
    private val listener: ClickOnItem<Service>
) :
    RecyclerView.Adapter<AgendarServicioAdapter.ViewHolder>() {

    private fun setData(servicios: List<Service>) {
        this.servicios = servicios
        notifyDataSetChanged()
    }

    fun isOneItemOrMoreSelect():Boolean = servicios.any { it.isSelect }

    class ViewHolder(val binding: ItemServicioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(servicio: Service) {
            with(binding) {
                NombreServicio.text = servicio.name
                if (servicio.isSelect) {
                    cardView.setCardBackgroundColor(itemView.getColor(R.color.cafe3))
                    NombreServicio.setTextColor(itemView.getColor(R.color.white))
                } else {
                    cardView.setCardBackgroundColor(itemView.getColor(R.color.cafe4))
                    NombreServicio.setTextColor(itemView.getColor(R.color.textcard))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemServicioBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = servicios[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.clikOnElement(element = item)
            servicios[position].isSelect = !item.isSelect
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = servicios.size


}