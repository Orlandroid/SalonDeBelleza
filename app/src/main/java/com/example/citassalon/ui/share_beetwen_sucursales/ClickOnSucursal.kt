package com.example.citassalon.ui.share_beetwen_sucursales

import com.example.citassalon.data.models.Sucursal

/** Esta Interface es usado por 2 Fragments
 *  los cuales son los siguientes
 * /ui/AgendarSucursal
 * /ui/info/InfoSucursal
 **/

interface ClickOnSucursal {

    fun clickOnSucursal(sucursal: Sucursal)

}