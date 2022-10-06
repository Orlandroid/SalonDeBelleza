package com.example.citassalon.ui.perfil.historial_citas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Appointment
import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.interfaces.ClickOnItem


class HistorialCitasAdapter(private val listener: ClickOnItem<Appointment>) :
    RecyclerView.Adapter<HistorialCitasAdapter.ViewHolder>() {


    private var listAppointment = arrayListOf<Appointment>()


    fun setData(lista: List<Appointment>) {
        listAppointment = lista as ArrayList<Appointment>
        notifyDataSetChanged()
    }

    fun removeElement(position: Int) {
        listAppointment.removeAt(position)
        notifyDataSetChanged()
    }

    fun getElement(position: Int): Appointment {
        return listAppointment[position]
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val sucursal: TextView = view.findViewById(R.id.tv_sucursal)
        val servicio: TextView = view.findViewById(R.id.tv_servicio)

        fun bind(appointment: Appointment) {
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
            listener.clikOnElement(appointment)
        }
        viewHolder.bind(appointment)
    }

    override fun getItemCount() = listAppointment.size

}
