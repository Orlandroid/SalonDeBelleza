package com.example.citassalon.ui.info.productos.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citassalon.data.models.Products
import com.example.citassalon.databinding.ItemProductBinding


class ProductsAdapter(private val listener: ProductsListener) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var listOfProducts: List<Products> = arrayListOf()

    fun setData(lista: List<Products>) {
        listOfProducts = lista
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Products) {
            Glide.with(itemView.context).load(products.image).into(binding.imageProduct)
            binding.productoName.text = products.title
            binding.productoPrice.text = products.price.toString()
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
            listener.clikcOnProduct(listOfProducts[position])
        }
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

    interface ProductsListener {
        fun clikcOnProduct(products: Products)
    }

}
