package com.example.citassalon.ui.info.productos.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citassalon.data.models.Product
import com.example.citassalon.databinding.ItemProductBinding
import com.example.citassalon.interfaces.ClickOnItem


class ProductsAdapter(private val listener: ClickOnItem<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var listOfProducts: List<Product> = arrayListOf()

    fun setData(lista: List<Product>) {
        listOfProducts = lista
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Product) {
            Glide.with(itemView.context).load(products.image).into(binding.imageProduct)
            binding.productoName.text = products.title
            binding.productoPrice.text = "$ ${products.price}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfProducts[position])
        holder.itemView.setOnClickListener {
            listener.clikOnElement(listOfProducts[position])
        }
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

}
