package com.example.citassalon.ui.perfil

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentPerfilBinding
import com.example.citassalon.util.AlertDialogWithButtons
import com.example.citassalon.util.AlertsDialogMessages
import com.example.citassalon.util.PERFIL_TO_HOME
import com.example.citassalon.util.navigate

class Perfil : Fragment(), AlertDialogWithButtons {


    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!


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
        return binding.root
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