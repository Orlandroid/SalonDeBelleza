package com.example.citassalon.presentacion.interfaces

interface ClickOnItem<T> {
    fun clickOnItem(element: T, position: Int? = null)
}