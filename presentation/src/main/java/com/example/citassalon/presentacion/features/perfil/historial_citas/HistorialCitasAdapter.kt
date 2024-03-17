package com.example.citassalon.presentacion.features.perfil.historial_citas

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.domain.perfil.AppointmentFirebase


class HistorialCitasAdapter(private val listener: ClickOnItem<AppointmentFirebase>) :
    RecyclerView.Adapter<HistorialCitasAdapter.ViewHolder>() {


    private var listAppointment = arrayListOf<AppointmentFirebase>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(lista: List<AppointmentFirebase>) {
        listAppointment = lista as ArrayList<AppointmentFirebase>
        notifyDataSetChanged()
    }


    fun getElement(position: Int): AppointmentFirebase {
        return listAppointment[position]
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val sucursal: TextView = view.findViewById(R.id.tv_sucursal)
        val servicio: TextView = view.findViewById(R.id.tv_servicio)

        fun bind(appointment: AppointmentFirebase) {
            sucursal.text = appointment.establishment
            servicio.text = appointment.service
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_appoinment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val appointment = listAppointment[position]
        viewHolder.itemView.setOnClickListener {
            listener.clickOnItem(appointment)
        }
        viewHolder.bind(appointment)
    }

    override fun getItemCount() = listAppointment.size

}
