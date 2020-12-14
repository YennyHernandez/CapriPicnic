package com.yennyh.capripicnic.models

import java.util.regex.Pattern


data class Patterns(
    val name:Pattern = Pattern.compile(
        "^" +
                "/\\S+/" + // any non-whitespace
                "(?=\\.*[0-9])" +         //no digits
                "(?=\\.*[@#$%^&+= ])" +    //no special character
                ".{5,}" +               //at least 5 characters
                "$",
    ),
    var lastName: Pattern = Pattern.compile(
        "^" +
                "(?=\\.*[0-9])" +         //no digits
                "(?=\\.*[@#$%^&+=])" +    //no special character
                ".{10,}" +               //at least 8 characters
                "$",
    ),
    var documentNumber: Pattern = Pattern.compile(
        "^" +
                "(?=\\S+$)" +           //no white spaces
                "/[a-zA-Z]+/g" +    //Matches any characters between a-z or A-Z
                "(?=\\.*[@#$%^&+= ])" +    //no special character
                ".{5,}" +               //at least 5 characters
                "$",
    ),
    var email: Pattern = android.util.Patterns.EMAIL_ADDRESS,
    var phone: Pattern = android.util.Patterns.PHONE,
    var address: Pattern = Pattern.compile(
        "^" +
                ".{5,}" +               //at least 5 characters
                "$",
    ),
    var password: Pattern = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$",
    )
)
