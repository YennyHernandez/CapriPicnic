package com.yennyh.capripicnic.models

data class User(
    var address: String? = null,
    var displayName: String? = null,
    var documentNumber: Long? = 0,
    var documentType: String? = null,
    var email: String ="",
    var idCarts: List<IdCart>? = listOf(IdCart("0")),
    var lastName: String = "",
    var name: String = "",
    var password: String? = null,
    var phone: Long = 0,
    var photoUrl: String? = "",
    var provider: String? = "BASIC",
    val role: String = "SUBSCRIBER",
    var uid: String? = null,
    var verified: Boolean = false
)