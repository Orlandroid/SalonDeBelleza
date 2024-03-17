package com.example.citassalon.presentacion.features.perfil.userprofile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.databinding.ItemUserInfoBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.domain.perfil.UserInfo

class UserProfileAdapter(private val listener: ClickOnItem<UserInfo>? = null) :
    RecyclerView.Adapter<UserProfileAdapter.ViewHolder>() {

    private var listOfUserInfo: List<UserInfo> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<UserInfo>) {
        listOfUserInfo = list
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: ItemUserInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userInfo: UserInfo) = with(binding) {
            tvTitle.text = userInfo.title
            tvNombre.text = userInfo.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserInfoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfUserInfo[position])
        holder.itemView.setOnClickListener {
            listener?.clickOnItem(listOfUserInfo[position])
        }
    }

    override fun getItemCount() = listOfUserInfo.size


}
