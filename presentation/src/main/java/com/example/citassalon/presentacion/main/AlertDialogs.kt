package com.example.citassalon.presentacion.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.citassalon.R
import com.example.citassalon.databinding.AlertDialogMessagesBinding


class AlertDialogs(
    private val kindOfMessage: Int = SUCCESS_MESSAGE,
    private val messageBody: String,
    private val clickOnAccept: ClickOnAccept? = null,
    private val isTwoButtonDialog: Boolean = false
) :
    DialogFragment() {

    private lateinit var binding: AlertDialogMessagesBinding

    companion object {
        const val SUCCESS_MESSAGE_COLOR = R.color.succes
        const val WARNING_MESSAGE_COLOR = R.color.waring
        const val ERROR_MESSAGE_COLOR = R.color.danger
        const val INFO_MESSAGE_COLOR = R.color.info
        const val SUCCESS_MESSAGE = 0
        const val WARNING_MESSAGE = 1
        const val ERROR_MESSAGE = 2
        const val INFO_MESSAGE = 3

    }

    interface ClickOnAccept {
        fun clickOnAccept()
        fun clickOnCancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlertDialogMessagesBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        isCancelable = false
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    private fun setUpUi() {
        with(binding) {
            buttonAceptarOneButton.setOnClickListener {
                clickOnAccept?.clickOnAccept()
                dialog?.dismiss()
            }
            buttonAceptar.setOnClickListener {
                clickOnAccept?.clickOnAccept()
                dialog?.dismiss()
            }
            buttonCancelar.setOnClickListener {
                clickOnAccept?.clickOnCancel()
                dialog?.dismiss()
            }
            binding.bodyMessage.text = messageBody
        }
        setKindOfMessage()
        setKindOfView(isTwoButtonDialog)
    }

    private fun setKindOfView(isTwoButtonDialog: Boolean) = with(binding) {
        if (isTwoButtonDialog) {
            buttonAceptarOneButton.visibility = View.GONE
            buttonAceptar.visibility = View.VISIBLE
            buttonCancelar.visibility = View.VISIBLE
        } else {
            buttonAceptarOneButton.visibility = View.VISIBLE
            buttonAceptar.visibility = View.GONE
            buttonCancelar.visibility = View.GONE
        }
    }

    private fun setKindOfMessage() = with(binding) {
        when (kindOfMessage) {
            0 -> {
                headerDialog.setCardBackgroundColor(resources.getColor(SUCCESS_MESSAGE_COLOR))
                titleHeader.text = "Succes"
            }
            1 -> {
                headerDialog.setCardBackgroundColor(resources.getColor(WARNING_MESSAGE_COLOR))
                titleHeader.text = "Warning"
            }
            2 -> {
                headerDialog.setCardBackgroundColor(resources.getColor(ERROR_MESSAGE_COLOR))
                titleHeader.text = "Error"
            }
            3 -> {
                headerDialog.setCardBackgroundColor(resources.getColor(INFO_MESSAGE_COLOR))
                titleHeader.text = "Info"
            }
        }
    }

}