package com.example.domain.perfil

data class ProfileItem(
    val nameResId: Int,
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
