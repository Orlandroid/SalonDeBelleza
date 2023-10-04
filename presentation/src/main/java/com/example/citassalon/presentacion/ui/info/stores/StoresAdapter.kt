package com.example.citassalon.presentacion.ui.info.stores

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.databinding.ItemStoreBinding
import com.example.citassalon.presentacion.ui.extensions.click

class StoresAdapter(private val clickOnStore: (Store) -> Unit) :
    RecyclerView.Adapter<StoresAdapter.ViewHolder>() {

    private var listOfStores: List<Store> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(lista: List<Store>) {
        listOfStores = lista
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            binding.textElement.text = store.name
            binding.root.click {
                clickOnStore(store)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStoreBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfStores[position])
    }

    override fun getItemCount() = listOfStores.size


    data class Store(
        val name: String = ""
    )


}
