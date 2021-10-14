package com.example.citassalon.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.displaySnack(message:String, length:Int= Snackbar.LENGTH_LONG, func: Snackbar.()->Unit){
    val snackBar= Snackbar.make(this,message,length)
    snackBar.func()
    snackBar.show()
}

fun Snackbar.action(message: String, listener:(View)->Unit){
    setAction(message,listener)
}