package com.example.citassalon.presentacion.ui.info.nuestro_staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.databinding.ItemOurStaffBinding
import com.example.domain.entities.remote.dummyUsers.User
import com.example.citassalon.presentacion.ui.extensions.loadImage

class OurStaffAdapter :
    RecyclerView.Adapter<OurStaffAdapter.ViewHolder>() {

    private var listOfCategories: List<User> = arrayListOf()

    fun setData(lista: List<User>) {
        listOfCategories = lista
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: ItemOurStaffBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) = with(binding) {
            fullName.text = "${user.firstName} ${user.lastName} ${user.maidenName}"
            email.text = user.email
            adress.text = user.address.address
            image.loadImage(user.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOurStaffBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfCategories[position])
    }

    override fun getItemCount(): Int {
        return listOfCategories.size
    }


}
