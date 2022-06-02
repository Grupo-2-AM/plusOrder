package com.grupo2.plusorder

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.backend.tables.BackendConta

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val loginBt = findViewById<View>(R.id.buttonLogin) as Button

        loginBt.setOnClickListener {

            // Try to login with email and pass from textviews
            var contaLogin = Conta(
                findViewById<TextInputEditText>(R.id.editTextEmail).text.toString(),
                findViewById<TextInputEditText>(R.id.editTextPassword).text.toString())
            var loggedConta: Conta? = BackendConta.LoginConta(contaLogin)

            // Check if login successful
            if (loggedConta == null) {
                Toast.makeText(applicationContext, "NAO", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(applicationContext, loggedConta.id.toString(), Toast.LENGTH_LONG);
            }
        }
    }
}