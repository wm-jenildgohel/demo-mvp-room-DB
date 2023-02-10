package com.example.demomvpdb.ui.login.interactor

import android.content.Context
import android.text.TextUtils
import com.example.demomvpdb.Utils
import com.example.demomvpdb.database.AppDatabase
import com.example.demomvpdb.model.User

class LoginInteractor {
    interface onLoginFinishedListener {
        fun onUserEmailError(s: String)
        fun onUserPasswordError(s: String)
        fun onLoginSuccess(user: User)
        fun onLoginError()
    }

    fun canLogin(
        user: User,
        listener: onLoginFinishedListener,
        context: Context
    ) {
        val database: AppDatabase? = AppDatabase.getInstance(context)
        if (TextUtils.isEmpty(user.email)) {
            listener.onUserEmailError("Email cannot be Empty !")
            return
        }
        if (TextUtils.isEmpty(user.password)) {
            listener.onUserPasswordError("Password cannot be Empty !")
            return
        }
        if (user.email?.let { Utils().emailValidations(it) } == false) {
            listener.onUserEmailError("Please Enter Valid Email !")
            return
        }
        if (user.password?.let { Utils().passwordValidations(it) } == false) {
            listener.onUserPasswordError("Password length must be greater than 8 and also contains special and capital characters !")
            return
        }
        if (user.email?.let {
                user.password?.let { it1 ->
                    database?.userDao()?.isExists(it, it1)
                }
            } == false) {
            listener.onUserEmailError("User Not Exists please signup !")
            listener.onLoginError()
            return
        }
        listener.onLoginSuccess(user)
    }
}