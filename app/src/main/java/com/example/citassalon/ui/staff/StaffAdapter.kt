package com.example.citassalon.ui.staff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citassalon.data.models.remote.migration.Staff
import com.example.citassalon.databinding.ItemStaffBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.ui.extensions.click

class StaffAdapter(private val listener: ClickOnItem<Staff>) :
    RecyclerView.Adapter<StaffAdapter.ViewHolder>() {

    private var listaStaff: List<Staff> = arrayListOf()

    fun setData(lista: List<Staff>) {
        listaStaff = lista
        notifyDataSetChanged()
    }

    fun getData(): List<Staff> = listaStaff

    class ViewHolder(val binding: ItemStaffBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(staff: Staff, listener: ClickOnItem<Staff>) {
            with(binding) {
                Glide.with(itemView.context).load(staff.image_url).into(staffPicture)
                staffName.text = staff.nombre
                staffPicture.click {
                    listener.clikOnElement(staff)
                }
                binding.root.click {
                    listener.clikOnElement(staff, 0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStaffBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentStaff = listaStaff[position]
        holder.bind(currentStaff, listener)
    }

    override fun getItemCount(): Int {
        return listaStaff.size
    }

}


