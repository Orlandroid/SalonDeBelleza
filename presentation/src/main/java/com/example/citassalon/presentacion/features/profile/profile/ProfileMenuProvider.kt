package com.example.citassalon.presentacion.features.profile.profile

import com.example.citassalon.R
import com.example.domain.perfil.MENU
import com.example.domain.perfil.ProfileItem

object ProfileMenuProvider {

    fun getMenusProfile(): List<ProfileItem> {
        val elementsMenu = arrayListOf<ProfileItem>()
        val profile = ProfileItem("Perfil", R.drawable.perfil, MENU.PROFILE)
        val history = ProfileItem("Historial de citas", R.drawable.historial_menu, MENU.HISTORY)
        val contactUs = ProfileItem("Contactanos", R.drawable.contactos, MENU.CONTACTS)
        val terminos =
            ProfileItem("Terminos y condiciones", R.drawable.terminos, MENU.TERMS_AND_CONDITIONS)
        val closeSession = ProfileItem("Cerrar sesion", R.drawable.cerrar, MENU.CLOSE_SESSION)
        elementsMenu.add(profile)
        elementsMenu.add(history)
        elementsMenu.add(contactUs)
        elementsMenu.add(terminos)
        elementsMenu.add(closeSession)
        return elementsMenu
    }
}