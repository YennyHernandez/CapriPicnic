package com.yennyh.capripicnic.models

import java.io.Serializable

class ThematicsPicnics(
    val id: String? = "",
    val name: String = "",
    val description: String = "",
    val extras_decorations: ArrayList<String> = arrayListOf(),
    val menus_id: ArrayList<String> = arrayListOf(),
    val photos: ArrayList<String> = arrayListOf()

) : Serializable