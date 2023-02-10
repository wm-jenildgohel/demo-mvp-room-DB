package com.example.demomvpdb

import android.content.Context
import android.content.SharedPreferences.Editor
import com.example.demomvpdb.model.User

class SharedPreference(context: Context) {
    private val myPref: String = "user_login"
    private  val LoginEmail: String = "email"
    private  val LoginPassword: String = "password"

    private var sharedPreferences = context.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    private var editor: Editor = sharedPreferences.edit()

    fun save(user: User) {
        editor.putString(LoginEmail, user.email)
        editor.putString(LoginPassword, user.password)
        editor.commit()
    }

    fun getUser(): User {
        val email = sharedPreferences.getString(LoginEmail, null)
        val password = sharedPreferences.getString(LoginPassword, null)

        return User(0, email, password)

    }

    fun clearLogin() {
        editor.putString(LoginEmail, null)
        editor.putString(LoginPassword, null)
        editor.commit()
    }



}