package com.example.citassalon.presentacion.features.profile.profile

import com.example.citassalon.R
import com.example.domain.perfil.MENU
import com.example.domain.perfil.ProfileItem

object ProfileMenuProvider {

    fun getMenusProfile(): List<ProfileItem> {
        val elementsMenu = arrayListOf<ProfileItem>()
        val profile = ProfileItem(R.string.profile, R.drawable.perfil, MENU.PROFILE)
        val history = ProfileItem(R.string.appointment_history, R.drawable.historial_menu, MENU.HISTORY)
        val contactUs = ProfileItem(R.string.contact_us, R.drawable.contactos, MENU.CONTACTS)
        val terminos =
            ProfileItem(R.string.terms_and_conditions, R.drawable.terminos, MENU.TERMS_AND_CONDITIONS)
        val closeSession = ProfileItem(R.string.logout, R.drawable.cerrar, MENU.CLOSE_SESSION)
        elementsMenu.add(profile)
        elementsMenu.add(history)
        elementsMenu.add(contactUs)
        elementsMenu.add(terminos)
        elementsMenu.add(closeSession)
        return elementsMenu
    }
}