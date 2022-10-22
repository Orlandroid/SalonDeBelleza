package com.example.citassalon.ui.perfil.perfil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentPerfilBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.ui.MainActivity
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.navigate
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PerfilFragment : BaseFragment<FragmentPerfilBinding>(R.layout.fragment_perfil),
    ListenerAlertDialogWithButtons, ClickOnItem<String> {

    private val viewModelPerfil: PerfilViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        setUpRecyclerView()
        with(binding) {
            toolbar.toolbarTitle.text = "Perfil"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun getListener(): ClickOnItem<String> {
        return this
    }

    private fun setUpRecyclerView() {
        binding.recyclerProfile.adapter = PerfilAdapter(setElementsMenu(), getListener())
    }

    private fun setElementsMenu(): List<PerfilAdapter.PerfilItem> {
        val elementsMenu = arrayListOf<PerfilAdapter.PerfilItem>()
        val perfil = PerfilAdapter.PerfilItem("Perfil", R.drawable.perfil)
        val historial = PerfilAdapter.PerfilItem("Historial de citas", R.drawable.historial_menu)
        val contactanos = PerfilAdapter.PerfilItem("Contactanos", R.drawable.contactos)
        val terminos = PerfilAdapter.PerfilItem("Terminos y condiciones", R.drawable.terminos)
        val cerrarSesion = PerfilAdapter.PerfilItem("Cerrar sesion", R.drawable.cerrar)
        elementsMenu.add(perfil)
        elementsMenu.add(historial)
        elementsMenu.add(contactanos)
        elementsMenu.add(terminos)
        elementsMenu.add(cerrarSesion)
        return elementsMenu
    }


    override fun clikOnElement(element: String, position: Int?) {
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