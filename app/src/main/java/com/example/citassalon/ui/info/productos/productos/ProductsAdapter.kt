package com.example.citassalon.ui.info.productos.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.databinding.ItemProductBinding
import com.example.citassalon.ui.extensions.click


class ProductsAdapter(private val listener: ClickOnItems) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var listOfProducts: List<Product> = arrayListOf()

    fun setData(lista: List<Product>) {
        listOfProducts = lista
        notifyDataSetChanged()
    }

    interface ClickOnItems {
        fun clickOnElement(product: Product)
        fun clickOnAddToCard(product: Product)
    }


    class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, listener: ClickOnItems) {
            with(binding) {
                Glide.with(itemView.context).load(product.image).into(imageProduct)
                productoName.text = product.title
                productoPrice.text = "$ ${product.price}"
                root.click {
                    listener.clickOnElement(product)
                }
                add.click {
                    listener.clickOnAddToCard(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfProducts[position], listener)
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

}
