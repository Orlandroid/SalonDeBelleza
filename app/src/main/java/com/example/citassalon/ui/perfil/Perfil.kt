package com.example.citassalon.ui.perfil

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentPerfilBinding
import com.example.citassalon.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Perfil : Fragment(), ListenerAlertDialogWithButtons {


    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private val viewModelPerfil: ViewModelPerfil by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        binding.buttonTermAdnCondictions.setOnClickListener {
            showTermAndCondition()
        }
        binding.buttonCerrarSession.setOnClickListener {
            logout()
        }
        binding.buttonHistorialDeCitas.setOnClickListener {
            navigate(PERFIL_TO_HISTORIAL_DE_CITAS)
        }
        setUpObserver()
        return binding.root
    }

    private fun setUpObserver() {
        viewModelPerfil.firebaseUser.observe(viewLifecycleOwner, {
            binding.nombreUsuario.text = it.email
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
        navigate(PERFIL_TO_HOME)
    }

    override fun clickOnCancel() {

    }

}