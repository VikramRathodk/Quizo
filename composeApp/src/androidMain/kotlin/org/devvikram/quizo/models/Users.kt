package org.devvikram.quizo.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Users(
    @SerialName("user_id")
    val userId: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("password")
    val password: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("phone")
    val phone: String = "",
)