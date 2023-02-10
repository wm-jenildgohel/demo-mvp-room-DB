package com.example.demomvpdb.ui.signup.interactor

import android.content.Context
import android.text.TextUtils
import com.example.demomvpdb.Utils
import com.example.demomvpdb.database.AppDatabase
import com.example.demomvpdb.model.User

class SignupInteractor {
    interface onSignupFinishedListener {
        fun onUserEmailError(s: String)
        fun onUserPasswordError(s: String)
        fun onUserRePasswordError(s: String)
        suspend fun onSignupSuccess(userEmail: User)
        fun onSignupError()
    }

    suspend fun doSignup(
        user: User,
        rePassword: String,
        listener: onSignupFinishedListener,
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
        if (user.password?.equals(rePassword) == false) {
            listener.onUserRePasswordError("Password doesnot match !")
            return
        }
        if (user.email?.let {
                user.password?.let { it1 ->
                    database?.userDao()?.isExists(it, it1)
                }
            } == true) {
            listener.onUserEmailError("User Already Exists !")
            listener.onSignupError()
            return
        }
        listener.onSignupSuccess(user)
    }
}