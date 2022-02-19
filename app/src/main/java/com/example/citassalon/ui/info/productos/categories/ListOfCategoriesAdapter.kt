package com.example.citassalon.ui.info.productos.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.databinding.RowElementoInfoBinding

class ListOfCategoriesAdapter(private val listener: ListOfcategoriesListener) :
    RecyclerView.Adapter<ListOfCategoriesAdapter.ViewHolder>() {

    private var listOfCategories: List<String> = arrayListOf()

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
            listener.clikcOnCategorie(listOfCategories[position])
        }
    }

    override fun getItemCount(): Int {
        return listOfCategories.size
    }

    interface ListOfcategoriesListener {
        fun clikcOnCategorie(categoria: String)
    }

}
