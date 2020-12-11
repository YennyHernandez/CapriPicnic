package com.yennyh.capripicnic.models

import java.io.Serializable

data class UserResponse(
    val uid: String,
    val name: String,
    val lastName: String,
    val documentType: String,
    val documentNumber: Int,
    val email: String,
    val phone: Long?,
    val photo: String?,
    val address: String?,
    val carts: List<Cart>?,
    val verified: Boolean,
    val role: String
): Serializable

data class User(
    val name: String,
    val lastName: String,
    val documentType: String,
    val documentNumber: Int,
    val email: String,
    val password: String,
    val phone: Long?,
    val photo: String?,
    val address: String?,
    val role: String
): Serializable

