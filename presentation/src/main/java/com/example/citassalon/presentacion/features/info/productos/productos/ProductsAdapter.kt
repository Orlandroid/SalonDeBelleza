package com.example.citassalon.presentacion.features.info.productos.productos

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.citassalon.R
import com.example.citassalon.databinding.ItemProductBinding
import com.example.citassalon.presentacion.features.extensions.click
import com.example.citassalon.presentacion.features.extensions.toBase64
import com.example.domain.entities.remote.Product


class ProductsAdapter(private val listener: ClickOnItems) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var listOfProducts: List<Product> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
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
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(product.image)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            product.imageBase64 = resource.toBase64()
                            Glide.with(itemView.context).load(resource)
                                .placeholder(R.drawable.loading_animation)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(imageProduct)
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imageProduct.setImageResource(R.drawable.ic_baseline_broken_image_24)
                        }
                    })
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
