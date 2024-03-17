package com.example.citassalon.presentacion.features.perfil.perfil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.databinding.ItemPerfilBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.domain.perfil.ProfileItem

class PerfilAdapter(
    private val menuElements: List<ProfileItem>,
    private val listener: ClickOnItem<String>
) :
    RecyclerView.Adapter<PerfilAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPerfilBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: ProfileItem) {
            binding.textMenu.text = menu.name
            binding.imageItemProfile.setImageResource(menu.image)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemPerfilBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(menuElements[position])
        viewHolder.itemView.setOnClickListener {
            listener.clickOnItem("",position + 1)
        }
    }

    override fun getItemCount() = menuElements.size


}
