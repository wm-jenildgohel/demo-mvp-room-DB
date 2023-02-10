package com.example.demomvpdb.ui.signup.view

import com.example.demomvpdb.model.User

interface SignupView {
    fun setUserEmailError(s: String)
    fun setPasswordError(s: String)
    fun setRePasswordError(s: String)
    fun showProggressBar()
    fun hideProggressBar()
    suspend fun onSignupSuccess(user: User)
    fun onSignupError()
    fun clearErrors()
}