package com.example.citassalon.main

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.citassalon.R
import com.example.citassalon.databinding.AlertDialogMessagesBinding


class AlertDialogs(
    private val kindOfMessage: Int = SUCCES_MESSAGE,
    private val messageBody: String,
    private val clikOnAccept: ClickOnAccept? = null
) :
    DialogFragment() {

    private lateinit var binding: AlertDialogMessagesBinding

    companion object {
        const val SUCCES_MESSAGE_COLOR = R.color.succes
        const val WARNING_MESSAGE_COLOR = R.color.waring
        const val ERROR_MESSAGE_COLOR = R.color.danger
        const val SUCCES_MESSAGE = 0
        const val WARNING_MESSAGE = 1
        const val ERROR_MESSAGE = 2
    }

    interface ClickOnAccept {
        fun clikOnAccept()
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
            buttonAceptar.setOnClickListener {
                clikOnAccept?.clikOnAccept()
                dialog?.dismiss()
            }
            binding.bodyMessage.text = messageBody
        }
        setKindOfMessage()
    }

    private fun setKindOfMessage() {
        when (kindOfMessage) {
            0 -> {
                binding.headerDialog.setCardBackgroundColor(resources.getColor(SUCCES_MESSAGE_COLOR))
                binding.titleHeader.text = "Succes"
            }
            1 -> {
                binding.headerDialog.setCardBackgroundColor(resources.getColor(WARNING_MESSAGE_COLOR))
                binding.titleHeader.text = "Warning"
            }
            2 -> {
                binding.headerDialog.setCardBackgroundColor(resources.getColor(ERROR_MESSAGE_COLOR))
                binding.titleHeader.text = "Error"
            }
        }
    }

}