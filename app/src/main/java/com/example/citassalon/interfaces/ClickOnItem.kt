package com.example.citassalon.interfaces

interface ClickOnItem<T> {
    fun clikOnElement(element:T,position:Int?=null)
}