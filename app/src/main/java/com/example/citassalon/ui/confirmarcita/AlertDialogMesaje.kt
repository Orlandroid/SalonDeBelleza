package com.example.citassalon.ui.confirmarcita

import android.content.Context
import com.example.citassalon.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialogMesaje {

    private var confirmacion = false

    fun showAlertDialog(context: Context, titulo: String, mensaje: String) {
        val alert = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        alert.setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Confirmar") { dialog, _ ->
                confirmacion = true
                dialog.dismiss()

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                confirmacion = false
                dialog.dismiss()
            }
        alert.create()
        alert.show()
    }

    fun getConfirmacion(): Boolean = confirmacion

}