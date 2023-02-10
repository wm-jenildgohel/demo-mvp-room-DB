package com.example.demomvpdb.ui.login.presenter

import android.content.Context
import com.example.demomvpdb.model.User
import com.example.demomvpdb.ui.login.interactor.LoginInteractor
import com.example.demomvpdb.ui.login.view.LoginView

class LoginPresenter(
    private val loginView: LoginView,
    private val loginInteractor: LoginInteractor
) :
    LoginInteractor.onLoginFinishedListener {

    override fun onUserEmailError(s: String) {
        loginView.setUserEmailError(s)
    }

    override fun onUserPasswordError(s: String) {
        loginView.setPasswordError(s)
    }

    override fun onLoginSuccess(user: User) {
        loginView.onLoginSuccess(user)
    }

    override fun onLoginError() {
        loginView.onLoginError()
    }

    fun validateUser(user: User, context: Context) {
        if (loginView != null) {
            loginInteractor.canLogin(user, this, context)
        }
    }
}
