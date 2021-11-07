package com.example.citassalon.ui.perfil.historial_citas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.R
import com.example.citassalon.data.models.Appointment


class AdaptadorHistorialCitas(private val listAppointment: List<Appointment>) :
    RecyclerView.Adapter<AdaptadorHistorialCitas.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val sucursal: TextView = view.findViewById(R.id.tv_sucursal)
        val empleado: TextView = view.findViewById(R.id.tv_empleado)
        val servicio: TextView = view.findViewById(R.id.tv_servicio)
        val fecha: TextView = view.findViewById(R.id.tv_fecha)
        val hora: TextView = view.findViewById(R.id.tv_hora)
        val total: TextView = view.findViewById(R.id.tv_total)

        fun bind(appointment: Appointment) {
            sucursal.text = appointment.establecimeinto
            empleado.text = appointment.empleado
            servicio.text = appointment.servicio
            fecha.text = appointment.fecha
            hora.text = appointment.hora
            total.text = "$ ${appointment.total}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_appoinment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val appointment = listAppointment[position]
        viewHolder.bind(appointment)
    }

    override fun getItemCount() = listAppointment.size

}
