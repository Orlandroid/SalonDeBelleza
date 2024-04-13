package com.example.domain.perfil

data class ProfileItem(
    val name: String,
    val image: Int,
    val menu: MENU
)

enum class MENU {
    PROFILE,
    HISTORY,
    CONTACTS,
    TERMS_AND_CONDITIONS,
    CLOSE_SESSION
}
