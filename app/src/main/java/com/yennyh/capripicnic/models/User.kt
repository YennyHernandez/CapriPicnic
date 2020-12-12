package com.yennyh.capripicnic.models

data class User(
    val address: String? = "",
    val documentNumber: Int? = 0,
    val documentType: String? ="",
    val email: String ="",
    val idCarts: List<IdCart>? = arrayListOf(),
    val lastName: String = "",
    val name: String = "",
    val password: String? = "",
    val phone: Long = 0,
    val photoUrl: String? = "",
    val role: String = "SUBSCRIBER",
    var uid: String? = "",
    val verified: Boolean = false
)