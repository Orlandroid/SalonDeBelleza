package com.example.citassalon.presentacion.interfaces

interface ClickOnItem<T> {
    fun clikOnElement(element:T,position:Int?=null)
}