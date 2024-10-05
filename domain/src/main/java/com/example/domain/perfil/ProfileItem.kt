package com.example.domain.perfil

data class ProfileItem(
    val name: String,
    val image: Int,
    val menu: MENU
) {
    companion object {
        private fun mockProfile(image: Int) = ProfileItem(
            name = "Proflie",
            image = image,
            menu = MENU.PROFILE
        )

        fun mockProfileList(image: Int) = listOf(
            mockProfile(image),
            mockProfile(image),
            mockProfile(image),
            mockProfile(image),
        )
    }

}

enum class MENU {
    PROFILE,
    HISTORY,
    CONTACTS,
    TERMS_AND_CONDITIONS,
    CLOSE_SESSION
}
