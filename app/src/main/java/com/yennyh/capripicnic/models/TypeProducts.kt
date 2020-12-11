package com.yennyh.capripicnic.models

import java.io.Serializable

class TypeProducts(
    val id: String? = "",
    val description: String = "",
    val photos: String = "",
    val extras: ArrayList<String> = arrayListOf(),
    val name: String = ""


) : Serializable