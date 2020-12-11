package com.yennyh.capripicnic.models

data class Item(
    val PurchaseType: List<PurchaseType>,
    val idCart: String,
    val idProduct: String,
    val idPurchaseInvoice: String
)