package com.yennyh.capripicnic.models

import java.io.Serializable

class TypeProducts(
    val id: String? = "",
    val description: String = "",
    val extras: ArrayList<String> = arrayListOf(),
    val name: String = "",
    val photos: ArrayList<String> = arrayListOf()

) : Serializable