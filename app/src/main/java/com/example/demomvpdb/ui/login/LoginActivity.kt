package com.example.demomvpdb.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demomvpdb.MainActivity
import com.example.demomvpdb.SharedPreference
import com.example.demomvpdb.databinding.ActivityLoginBinding
import com.example.demomvpdb.model.User
import com.example.demomvpdb.ui.login.interactor.LoginInteractor
import com.example.demomvpdb.ui.login.presenter.LoginPresenter
import com.example.demomvpdb.ui.login.view.LoginView
import com.example.demomvpdb.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var sharedPreference: SharedPreference

    private var loginInteractor: LoginInteractor = LoginInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        loginPresenter = LoginPresenter(this, loginInteractor)
        sharedPreference = SharedPreference(this)

        binding.btnLogin.setOnClickListener {
            showProggressBar()
            val userEmail = binding.etLoginEmail.text.toString().trim()
            val userPassword = binding.etLoginPassword.text.toString().trim()
            val user = User(null, userEmail, userPassword)
            loginPresenter.validateUser(user, this)
        }

        binding.tvGoToSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        setContentView(binding.root)
    }


    override fun setUserEmailError(s: String) {
        hideProggressBar()
        binding.tlLoginEmail.error = s
    }

    override fun setPasswordError(s: String) {
        hideProggressBar()
        binding.tlLoginPassword.error = s
    }

    override fun showProggressBar() {
        binding.proggressBar.visibility = View.VISIBLE
    }

    override fun hideProggressBar() {
        binding.proggressBar.visibility = View.GONE
    }

    override fun onLoginSuccess(user: User) {
        hideProggressBar()
        sharedPreference.save(user)
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onLoginError() {
        hideProggressBar()
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show()
    }
}