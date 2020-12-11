package com.yennyh.capripicnic.models

data class PicnicView(
    val id: String,
    val idMenus: List<IdMenu>,
    val idProducts: List<IdProductX>,
    val price: String,
    val thematic: String
)