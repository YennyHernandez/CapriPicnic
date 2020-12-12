package com.yennyh.capripicnic.models

data class Menu(
    val id: String = "",
    val idProducts: List<IdProduct> = arrayListOf(),
    val name: String = "",
    val price: String = ""
)