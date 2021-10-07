package com.example.citassalon.ui.confirmarcita

import android.content.Context
import com.example.citassalon.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialogMesaje(private val context: Context, private val listener: ListenerAlertDialog) {

    fun showAlertDialog() {
        val alert = MaterialAlertDialogBuilder(context)
        alert.setTitle(R.string.confirma_cita)
            .setMessage(R.string.mensaje_confirmacion)
            .setPositiveButton(R.string.confirmar) { dialog, _ ->
                listener.clickOnConfirmar()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancelar) { dialog, _ ->
                listener.clickOnCancel()
                dialog.dismiss()
            }
        alert.create()
        alert.show()
    }


}