package com.example.demomvpdb.ui.signup.presenter

import android.content.Context
import com.example.demomvpdb.model.User
import com.example.demomvpdb.ui.signup.interactor.SignupInteractor
import com.example.demomvpdb.ui.signup.view.SignupView

class SignupPresenter(
    private val signupView: SignupView,
    private val signupInteractor: SignupInteractor
) :
    SignupInteractor.onSignupFinishedListener {

    override fun onUserEmailError(s: String) {
        signupView.setUserEmailError(s)
    }

    override fun onUserPasswordError(s: String) {
        signupView.setPasswordError(s)
    }

    override fun onUserRePasswordError(s: String) {
        signupView.setRePasswordError(s)
    }

    override suspend fun onSignupSuccess(user: User) {
        signupView.onSignupSuccess(user)
    }

    override fun onSignupError() {
        signupView.onSignupError()
    }


    suspend fun validateUser(user: User, rePassword: String, context: Context) {
        signupInteractor.doSignup(user, rePassword, this, context)
    }
}