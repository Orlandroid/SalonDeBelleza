package com.example.citassalon.presentacion.features.perfil.perfil

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentPerfilBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.domain.perfil.ProfileItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PerfilFragment : BaseFragment<FragmentPerfilBinding>(R.layout.fragment_perfil),
    ListenerAlertDialogWithButtons, ClickOnItem<String> {

    private val viewModelPerfil: PerfilViewModel by viewModels()


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.perfil)
    )

    override fun setUpUi() {
        setUpRecyclerView()
    }

    private fun getListener(): ClickOnItem<String> {
        return this
    }

    private fun setUpRecyclerView() {
        binding.recyclerProfile.adapter = PerfilAdapter(setElementsMenu(), getListener())
    }

    private fun setElementsMenu(): List<ProfileItem> {
        val elementsMenu = arrayListOf<ProfileItem>()
        val perfil = ProfileItem("Perfil", R.drawable.perfil)
        val historial = ProfileItem("Historial de citas", R.drawable.historial_menu)
        val contactanos = ProfileItem("Contactanos", R.drawable.contactos)
        val terminos = ProfileItem("Terminos y condiciones", R.drawable.terminos)
        val cerrarSesion = ProfileItem("Cerrar sesion", R.drawable.cerrar)
        elementsMenu.add(perfil)
        elementsMenu.add(historial)
        elementsMenu.add(contactanos)
        elementsMenu.add(terminos)
        elementsMenu.add(cerrarSesion)
        return elementsMenu
    }


    override fun clickOnItem(element: String, position: Int?) {
        when (position) {
            1 -> {
                val action = PerfilFragmentDirections.actionPerfilToUserProfileFragment()
                navigate(action)
            }

            2 -> {
                val action = PerfilFragmentDirections.actionPerfilToHistorialDeCitas()
                navigate(action)
            }

            3 -> Log.w("uno", "unos")
            4 -> showTermAndCondition()
            5 -> logout()
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModelPerfil.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->
            firebaseUser?.let {
                binding.nombreUsuario.text = it.email
            }
        }
    }

    private fun logout() {
        val alert = AlertsDialogMessages(requireContext())
        alert.alertPositiveAndNegative(
            this,
            resources.getString(R.string.cerrar_session),
            resources.getString(R.string.seguro_que_deseas_cerrar_sesion)
        )
    }

    private fun showTermAndCondition() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showTermAndConditions()
    }


    override fun clickOnConfirmar() {
        viewModelPerfil.logout()
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun clickOnCancel() {

    }

}