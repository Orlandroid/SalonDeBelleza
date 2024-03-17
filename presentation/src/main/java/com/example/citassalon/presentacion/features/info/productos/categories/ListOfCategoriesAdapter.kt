package com.example.citassalon.presentacion.features.info.productos.categories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.databinding.RowElementoInfoBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem

class ListOfCategoriesAdapter(private val listener: ClickOnItem<String>) :
    RecyclerView.Adapter<ListOfCategoriesAdapter.ViewHolder>() {

    private var listOfCategories: List<String> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(lista: List<String>) {
        listOfCategories = lista
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: RowElementoInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoria: String) {
            binding.textElement.text = categoria
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowElementoInfoBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfCategories[position])
        holder.itemView.setOnClickListener {
            listener.clickOnItem(listOfCategories[position])
        }
    }

    override fun getItemCount(): Int {
        return listOfCategories.size
    }


}
