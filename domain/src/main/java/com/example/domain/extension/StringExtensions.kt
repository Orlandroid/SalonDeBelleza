package com.example.domain.extension

fun String?.toInitials(): String =
    this
        ?.split(" ")
        ?.filter { it.isNotBlank() }
        ?.take(2)
        ?.joinToString("") { it.first().uppercase() }
        .orEmpty()