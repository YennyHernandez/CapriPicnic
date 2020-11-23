package com.yennyh.capripicnic.models

data class ThematicsPicnics (
    val id: String?,
    val name:String ,
    val description: String,
    val extras_decorations: ArrayList<String>,
    val menus_id: ArrayList<String> ,
    val photos: ArrayList<String>
)
