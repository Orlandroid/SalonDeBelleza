package com.example.citassalon.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.citassalon.R
import com.example.citassalon.databinding.AlertDialogForgetPasswordBinding
import com.example.citassalon.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.ui.login.ListeneClickOnRecoverPassword
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * This class Show AlertDialog for
 *  the whole application
 * **/

class AlertsDialogMessages(private val context: Context) {

    /** @param listener
     *  Show one AlertDialog to confirm appointment
     * **/
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

    /**
     *  Show one alertialog with two button acept and cancel
     *  alse you can add you own title and body message
     * */
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
            /**Esto lo que hace es que solo con dimmiss se puedo quitar el alert
             * ya que sin esto presionado en cual parte de la pantalla en alert de quitaba*/
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