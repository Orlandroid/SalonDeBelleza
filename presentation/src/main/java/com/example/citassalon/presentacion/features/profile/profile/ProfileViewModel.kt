package com.example.citassalon.presentacion.features.profile.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.R
import com.example.data.preferences.LoginPreferences
import com.example.domain.perfil.MENU
import com.example.domain.perfil.ProfileItem
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val loginPeferences: LoginPreferences
) :
    ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> get() = _firebaseUser

    fun destroyUserSession() {
        loginPeferences.destroyUserSession()
    }

    fun logout() {
        repository.logout()
    }

    init {
        _firebaseUser.value = repository.getUser()
    }

    fun setElementsMenu(): List<ProfileItem> {
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