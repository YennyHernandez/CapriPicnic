package com.yennyh.capripicnic.models

data class Cart(
        val id: String,
        val idInvoice: String,
        val idProducts: List<IdProduct>,
)