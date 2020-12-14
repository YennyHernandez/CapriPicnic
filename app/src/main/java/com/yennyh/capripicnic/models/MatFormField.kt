package com.yennyh.capripicnic.models

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

data class MatFormField(
    var editText: TextInputEditText? = null,
    var editTextLayout: TextInputLayout? = null,
    var pattern: Pattern? = Pattern.compile(""),
    var valid: Boolean? = true,
    var errorMessages: ArrayList<String>? = arrayListOf()
)

