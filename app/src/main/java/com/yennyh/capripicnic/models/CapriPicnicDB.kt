package com.yennyh.capripicnic.models

data class CapriPicnicDB(
    val invoices: List<Invoice> = arrayListOf(),
    val menus: List<Menu> = arrayListOf(),
    val picnicView: List<PicnicView> = arrayListOf(),
    val products: List<Product> = arrayListOf(),
    val unit: List<Unit> = arrayListOf(),
    val users: List<User> = arrayListOf(),
    val carts: List<Cart>,
)