package com.yennyh.capripicnic.services

import android.app.Application
import android.widget.Toast
import com.yennyh.capripicnic.models.ErrorMessages
import com.yennyh.capripicnic.models.MatFormField
import com.yennyh.capripicnic.models.RegisterForm
import com.yennyh.capripicnic.models.User

open class FormsValidator: Application() {

    var valid = false
    var user = User()

    private val errorMessages = ErrorMessages()

    fun isValidRegisterForm(form: RegisterForm) {

//        for (field in 0..form){ //TODO: Iterar atributos
//            isValidField(field)
//        }
        valid = form.name?.let { isValidField(it) } == true
        valid = form.lasName?.let { isValidField(it) } == true
        valid = form.documentNumber?.let { isValidField(it) } == true
        valid = form.email?.let { isValidField(it) } == true
        valid = form.phone?.let { isValidField(it) } == true
        valid = form.address?.let { isValidField(it) } == true
        valid = form.password?.let { isValidField(it) } == true
        valid = form.passwordVerified?.let { isValidField(it) } == true
        // Patch user
        patchUser(form)
    }

    private fun isValidField(field: MatFormField): Boolean {
        if (field.editText!!.isInEditMode) {
            if (field.editText.toString().isEmpty()) {
                field.errorMessages?.add(errorMessages.required)
            } else {
                field.errorMessages?.remove(errorMessages.required)
            }
            Toast.makeText(baseContext,"Entra", Toast.LENGTH_SHORT).show()
        }
        field.editTextLayout?.error = "El campo es requerido"
        val pattern = field.pattern
        return pattern?.matcher(field.editText.toString())?.matches() ?: true
    }


    fun getValue(): User {
        return user
    }

    private fun patchUser(form: RegisterForm) {
        user.name = form.name?.editText?.text.toString()
        user.lastName = form.lasName?.editText?.text.toString()
        user.documentNumber = form.documentNumber?.editText?.text.toString().toLong()
        user.email = form.email?.editText?.text.toString()
        user.phone = form.phone?.editText?.text.toString().toLong()
        user.address = form.address?.editText?.text.toString()
        user.password = form.password?.editText?.text.toString()
    }

}