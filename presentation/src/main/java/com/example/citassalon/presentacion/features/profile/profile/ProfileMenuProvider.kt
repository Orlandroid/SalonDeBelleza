package com.example.citassalon.presentacion.features.profile.profile

import com.example.citassalon.R
import com.example.domain.perfil.MENU
import com.example.domain.perfil.ProfileItem

object ProfileMenuProvider {

    fun getMenusProfile(): List<ProfileItem> {
        val elementsMenu = arrayListOf<ProfileItem>()
        val perfil = ProfileItem("Perfil", R.drawable.perfil, MENU.PROFILE)
        val historial = ProfileItem("Historial de citas", R.drawable.historial_menu, MENU.HISTORY)
        val contactanos = ProfileItem("Contactanos", R.drawable.contactos, MENU.CONTACTS)
        val terminos =
            ProfileItem("Terminos y condiciones", R.drawable.terminos, MENU.TERMS_AND_CONDITIONS)
        val cerrarSesion = ProfileItem("Cerrar sesion", R.drawable.cerrar, MENU.CLOSE_SESSION)
        elementsMenu.add(perfil)
        elementsMenu.add(historial)
        elementsMenu.add(contactanos)
        elementsMenu.add(terminos)
        elementsMenu.add(cerrarSesion)
        return elementsMenu
    }
}