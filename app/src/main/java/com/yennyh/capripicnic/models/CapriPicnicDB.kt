package com.yennyh.capripicnic.models

data class CapriPicnicDB(
    val invoices: List<Invoice>,
    val menus: List<Menu>,
    val picnicView: List<PicnicView>,
    val products: List<Product>,
    val unit: List<Unit>,
    val users: List<User>
)