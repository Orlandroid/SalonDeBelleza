package com.example.citassalon.ui.perfil.perfil

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentPerfilBinding
import com.example.citassalon.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Perfil : Fragment(), ListenerAlertDialogWithButtons, ListenerClickOnElementsRecycler {


    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private val viewModelPerfil: ViewModelPerfil by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        setUpUi()
        setUpObserver()
        return binding.root
    }


    private fun setUpUi() {
        setUpRecyclerView()
        with(binding){
            toolbar.toolbarTitle.text="Perfil"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun getListener(): ListenerClickOnElementsRecycler {
        return this
    }

    private fun setUpRecyclerView() {
        binding.recyclerProfile.adapter = AdaptadorPerfil(setElementsMenu(), getListener())
    }

    private fun setElementsMenu(): List<PerfilItem> {
        val elementsMenu = arrayListOf<PerfilItem>()
        val perfil = PerfilItem("Perfil", R.drawable.perfil)
        val historial = PerfilItem("Historial de citas", R.drawable.historial_menu)
        val contactanos = PerfilItem("Contactanos", R.drawable.contactos)
        val terminos = PerfilItem("Terminos y condiciones", R.drawable.terminos)
        val cerrarSesion = PerfilItem("Cerrar sesion", R.drawable.cerrar)
        elementsMenu.add(perfil)
        elementsMenu.add(historial)
        elementsMenu.add(contactanos)
        elementsMenu.add(terminos)
        elementsMenu.add(cerrarSesion)
        return elementsMenu
    }

    override fun clickOnElement(position: Int) {
        when (position) {
            1 -> Log.w("uno", "unos")
            2 -> navigate(PERFIL_TO_HISTORIAL_DE_CITAS)
            3 -> Log.w("uno", "unos")
            4 -> showTermAndCondition()
            5 -> logout()
        }
    }

    private fun setUpObserver() {
        viewModelPerfil.firebaseUser.observe(viewLifecycleOwner, {
            if (it.email != null) {
                binding.nombreUsuario.text = it.email
            }
            Log.w("USER", it.tenantId.toString())
            Log.w("USER", it.uid.toString())
            Log.w("USER", it.displayName.toString())
            Log.w("USER", it.email.toString())
        })
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clickOnConfirmar() {
        viewModelPerfil.destroyUserSession()
        navigate(PERFIL_TO_HOME)
    }

    override fun clickOnCancel() {

    }


}