package com.yennyh.capripicnic.models

import android.media.Image

data class RegisterForm(
    var name: MatFormField? = MatFormField(),
    var lasName: MatFormField? =  MatFormField(),
    var documentNumber: MatFormField? =  MatFormField(),
    var email: MatFormField? =  MatFormField(),
    var phone: MatFormField? =  MatFormField(),
    var address: MatFormField? =  MatFormField(),
    var password: MatFormField? =  MatFormField(),
    var passwordVerified: MatFormField? =  MatFormField(),
    var photo: Image? = null,
)
