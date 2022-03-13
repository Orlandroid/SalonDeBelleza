package com.example.citassalon.data.models.rickandmorty

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CharacterResponse(
    val info: Info,
    val results:List<Character>
)

@Parcelize
data class Character(
    val id:Int,
    val name: String,
    val status:String,
    val species:String,
    val type:String,
    val gender:String,
    val origin: Origin,
    val location: Location,
    val image:String,
    val episode:List<String>,
    val url:String,
):Parcelable


@Parcelize
data class Location(
    val name:String,
    val url:String
):Parcelable

@Parcelize
data class Origin(
    val name:String,
    val url:String
):Parcelable