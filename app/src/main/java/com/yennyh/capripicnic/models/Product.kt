package com.yennyh.capripicnic.models

import java.io.Serializable
import kotlin.reflect.typeOf

data class Product(
    val baseUnit: String = "",
    val description: String = "",
    val id: String? = "",
    val name: String = "",
    val photos: List<Photo> = arrayListOf(Photo()),
    val price: String = ""
):Serializable