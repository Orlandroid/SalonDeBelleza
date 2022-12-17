package com.example.citassalon.ui.info.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.ui.extensions.base64StringToBitmap
import com.squareup.picasso.Picasso

class CartAdapter :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var listOfProducts: List<Product> = ArrayList()

    fun setData(lista: List<Product>) {
        listOfProducts = lista
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val imageProduct: ImageView = view.findViewById(R.id.image_product)
        private val productName: TextView = view.findViewById(R.id.tv_product_name)
        private val productPrice: TextView = view.findViewById(R.id.tv_price)
        fun bind(product: Product) {
            Glide.with(itemView.context).load(product.imageBase64.base64StringToBitmap())
                .into(imageProduct)
            Picasso.get().load(product.image).into(imageProduct)
            productName.text = product.title
            productPrice.text = "$ ${product.price}"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfProducts[position])
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

}