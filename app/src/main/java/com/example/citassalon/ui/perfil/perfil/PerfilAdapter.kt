package com.example.citassalon.ui.perfil.perfil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.databinding.ItemPerfilBinding
import com.example.citassalon.interfaces.ClickOnItem

class PerfilAdapter(
    private val menuElements: List<PerfilItem>,
    private val listener: ClickOnItem<String>
) :
    RecyclerView.Adapter<PerfilAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPerfilBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: PerfilItem) {
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
            listener.clikOnElement("",position + 1)
        }
    }

    override fun getItemCount() = menuElements.size

    data class PerfilItem(
        val name: String,
        val image: Int
    )

}
