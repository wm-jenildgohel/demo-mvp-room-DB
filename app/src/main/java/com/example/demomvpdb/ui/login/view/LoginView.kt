package com.example.demomvpdb.ui.login.view

import com.example.demomvpdb.model.User

interface LoginView {
    fun setUserEmailError(s: String)
    fun setPasswordError(s: String)
    fun showProggressBar()
    fun hideProggressBar()
    fun onLoginSuccess(user : User)
    fun onLoginError()
}