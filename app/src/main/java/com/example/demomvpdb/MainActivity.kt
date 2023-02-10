package com.example.demomvpdb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demomvpdb.database.AppDatabase
import com.example.demomvpdb.databinding.ActivityMainBinding
import com.example.demomvpdb.model.User
import com.example.demomvpdb.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreference: SharedPreference
    private var database: AppDatabase? = null
    private lateinit var intent: Intent
    var user: User? = null

    private fun isUserLogin(): Boolean {
        user = sharedPreference.getUser()
        if (user?.email?.let {
                user?.password?.let { it1 ->
                    database?.userDao()?.isExists(it, it1)
                }
            } == true) {
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreference = SharedPreference(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(this)

        binding.btnLogout.setOnClickListener {
            sharedPreference.clearLogin()
            Toast.makeText(this, "bye bye", Toast.LENGTH_SHORT).show()
            finish()
        }

        if (!isUserLogin()) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Welcome back ${user?.email}", Toast.LENGTH_SHORT).show()
        }

        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        binding.tvUserEmail.text = buildString {
            append("Hi, ")
            append(user?.email)
        }
    }
}