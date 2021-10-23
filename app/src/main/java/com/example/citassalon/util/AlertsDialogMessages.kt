package com.example.citassalon.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.citassalon.R
import com.example.citassalon.ui.confirmarcita.ListenerAlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * This class Show AlertDialog for
 *  the whole application
 * **/

class AlertsDialogMessages(private val context: Context) {

    /** @param listener
     *  Show one AlertDialog to confirm appointment
     * **/
    fun showComfirmationAppoinment(listener: ListenerAlertDialog) {
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


    fun showSimpleMessage(title: String, message: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.aceptar) { dialog, which ->
            }
            .show()
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