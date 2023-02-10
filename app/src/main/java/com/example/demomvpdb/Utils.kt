package com.example.demomvpdb

class Utils {
    fun emailValidations(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }

        return true
    }

    fun passwordValidations(password: String): Boolean {
        val passwordRegex: String = "(.*[@#!$%^&+=].*)" + "(.*[A-Z].*)" + "(.*\\d.*)"

        if (password.isEmpty()) {
            return false
        } else if (password.length < 8) {
            return false
        } else if (passwordRegex.toRegex().matches(password)) {
            return false
        }

        return true
    }

    fun retyepePasswordValidations(password: String, retypePassword : String): Boolean {
        if(password.equals(retypePassword)){
            return true
        }
        return false
    }
}