package org.devvikram.quizo.navigations

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {

    abstract val route: String

    @Serializable
    data object Home : Destination() {
        override val route: String = "home"
    }
    @Serializable
    data object Login : Destination() {
        override val route: String = "login"
    }


}