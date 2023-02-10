package com.example.demomvpdb.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.demomvpdb.MainActivity
import com.example.demomvpdb.SharedPreference
import com.example.demomvpdb.database.AppDatabase
import com.example.demomvpdb.databinding.ActivitySignupBinding
import com.example.demomvpdb.model.User
import com.example.demomvpdb.ui.login.LoginActivity
import com.example.demomvpdb.ui.signup.interactor.SignupInteractor
import com.example.demomvpdb.ui.signup.presenter.SignupPresenter
import com.example.demomvpdb.ui.signup.view.SignupView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity(), SignupView {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupPresenter: SignupPresenter
    private lateinit var sharedPreference: SharedPreference
    private var database: AppDatabase? = null

    private var signupInteractor: SignupInteractor = SignupInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        signupPresenter = SignupPresenter(this, signupInteractor)
        database = AppDatabase.getInstance(this)
        sharedPreference = SharedPreference(this)

        val context : Context = this

        binding.btnSignup.setOnClickListener {
            showProggressBar()
            val userEmail = binding.etSignupEmail.text.toString().trim()
            val userPassword = binding.etSignupPassword.text.toString().trim()
            val userRePassword = binding.etSignupRePassword.text.toString().trim()
            val user = User(null, userEmail, userPassword)
            lifecycleScope.launch {
                signupPresenter.validateUser(user, userRePassword, context)
            }
        }

        binding.tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        setContentView(binding.root)
    }

    override fun setUserEmailError(s: String) {
        hideProggressBar()
        clearErrors()
        binding.tlSignupEmail.error = s
    }

    override fun setPasswordError(s: String) {
        hideProggressBar()
        binding.tlSignupPassword.error = s
    }

    override fun setRePasswordError(s: String) {
        hideProggressBar()
        binding.tlSignupRePassword.error = s
    }

    override fun showProggressBar() {
        hideProggressBar()
        binding.proggressBar.visibility = View.VISIBLE
    }

    override fun hideProggressBar() {
        clearErrors()
        binding.proggressBar.visibility = View.GONE
    }

    override suspend fun onSignupSuccess(user: User) {
        database?.userDao()?.addUser(user)
        delay(400)
        Toast.makeText(this, "Account Created Successfully !", Toast.LENGTH_SHORT).show()
        hideProggressBar()
        sharedPreference.save(user)
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onSignupError() {
        hideProggressBar()
        Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT).show()
    }

    override fun clearErrors() {
        binding.tlSignupEmail.error = ""
        binding.tlSignupPassword.error = ""
        binding.tlSignupRePassword.error = ""
    }
}