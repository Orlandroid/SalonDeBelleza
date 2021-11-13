package com.example.citassalon.ui.login

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.citassalon.databinding.AlertDialogForgetPasswordBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ForgetPasswordDialog(private val listener: ListeneClickOnRecoverPassword) :
    DialogFragment() {

    private lateinit var binding: AlertDialogForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlertDialogForgetPasswordBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getUser(): String {
        return binding.forgetPassword.text.toString()
    }


    private fun setUpUi() {
        isCancelable = false
        binding.buttonCancelar.setOnClickListener {
            dialog?.dismiss()
        }
        binding.buttonForgetPasword.setOnClickListener {
            val userEmail = getUser()
            if (userEmail.isNotEmpty()) {
                listener.clickOnResetPassword(userEmail)
                dialog?.dismiss()
            }
        }
    }

}