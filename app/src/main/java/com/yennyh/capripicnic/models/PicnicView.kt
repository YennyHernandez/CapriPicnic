package com.yennyh.capripicnic.models

import java.io.Serializable

data class PicnicView(
    val description: String = "",
    val id: String = "",
    val idMenus: List<IdMenu> = arrayListOf(),
    val idProducts: List<IdProduct> = arrayListOf(),
    val name: String = "",
    val photos: List<Photo> = arrayListOf(),
    val price: String = "",
    val thematic: String = ""
):Serializable