package com.yennyh.capripicnic.models

import java.io.Serializable
import kotlin.reflect.typeOf

data class Product(
    val baseUnit: String = "",
    val description: String = "",
    val id: String? = "",
    val idProductType: String = "",
    val name: String = "",
    val photos: List<Photo> = arrayListOf(),
    val price: String = ""
):Serializable