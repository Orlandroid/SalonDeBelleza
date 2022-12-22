package com.example.citassalon.presentacion.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.citassalon.R
import com.example.citassalon.presentacion.interfaces.ListenerAlertDialogWithButtons
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AlertsDialogMessages(private val context: Context) {

    fun showComfirmationAppoinment(listener: ListenerAlertDialogWithButtons) {
        val view = LayoutInflater.from(context).inflate(R.layout.alert_confirmar_cita, null)
        val alert = MaterialAlertDialogBuilder(context)
            .setView(view)
            .setCancelable(false)
            .show()

        /**Setting cliks of buttons*/
        val comfirmar: Button = view.findViewById(R.id.alert_cita_comfirmar)
        val cancelar: Button = view.findViewById(R.id.alert_cita_cancelar)
        comfirmar.setOnClickListener {
            listener.clickOnConfirmar()
            alert.dismiss()
        }
        cancelar.setOnClickListener {
            listener.clickOnCancel()
            alert.dismiss()
        }
    }

    fun alertPositiveAndNegative(
        listener: ListenerAlertDialogWithButtons,
        title: String,
        bodyMessage: String
    ) {
        val view = LayoutInflater.from(context).inflate(R.layout.alert_button_aceptar_cancel, null)
        val alert = MaterialAlertDialogBuilder(context)
            .setView(view)
            .setCancelable(false)
            .show()

        val aceptar: Button = view.findViewById(R.id.alert_aceptar)
        val cancelar: Button = view.findViewById(R.id.alert_cancelar)
        val alertTitle: TextView = view.findViewById(R.id.alert_title)
        val alertBodyMessage: TextView = view.findViewById(R.id.alert_body_message)
        alertTitle.text = title
        alertBodyMessage.text = bodyMessage
        aceptar.setOnClickListener {
            listener.clickOnConfirmar()
            alert.dismiss()
        }
        cancelar.setOnClickListener {
            listener.clickOnCancel()
            alert.dismiss()
        }
    }

    fun showCustomAlert(messageP: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.alert_message, null)
        val customDialog = MaterialAlertDialogBuilder(context)
            .setView(view)
            .setCancelable(false)
            .show()
        val btDismiss = view.findViewById<Button>(R.id.btDismissCustomDialog)
        val message = view.findViewById<TextView>(R.id.alert_message_message)
        message.text = messageP
        btDismiss.setOnClickListener {
            customDialog.dismiss()
        }
    }


    fun showSimpleMessage(
        title: String,
        message: String,
        listener: ClickOnAccepSimpleDialog? = null
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.aceptar) { _, _ ->
                listener?.clikOnPositiveButton()
            }
            .show()
    }

    interface ClickOnAccepSimpleDialog {
        fun clikOnPositiveButton()
    }

    fun showTermAndConditions() {
        val alert = MaterialAlertDialogBuilder(
            context,
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
            .setTitle(R.string.terminosCondiciones)
            .setMessage(R.string.terminos_y_condiciones)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        alert.create()
        alert.show()
    }


}