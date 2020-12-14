package com.yennyh.capripicnic.models

data class ErrorMessages(
    val required: String = "El campo es requido",
    val min: String = "El al menos ",
    val max: String = "No puede ingresar más de ",
    val patternNoSpecialCharacters: String =
        "El campo no pude contener caracteres especiales y/o números",
    val patternPhone: String = "El campo solo puede contener números",
    val patternEmail: String = "El email es inválido",
    val patternSpecialCharacters: String = "El campo no tine al menos un caracter especial",
    val patternUpperCase: String = "El campo debe contener al menos un letra mínúscula"
)
