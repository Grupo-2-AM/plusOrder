package com.grupo2.plusorder

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.backend.tables.BackendConta

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val loginBt = findViewById<View>(R.id.buttonLogin) as Button

        loginBt.setOnClickListener{

            // Get email and password
            val emailText = findViewById<TextView>(R.id.editTextTextPassword).text as? String?
            val passText = findViewById<TextView>(R.id.editTextTextPassword2).text as? String?

            // Try to login with email and pass from textviews
            var contaLogin = Conta(emailText, passText)
            var loggedConta : Conta? = BackendConta.loginConta(contaLogin)

            // Check if login successful
            if (loggedConta == null)
            {
                Toast.makeText(applicationContext, "NAO", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(applicationContext, loggedConta.id.toString(), Toast.LENGTH_LONG);
            }

    }
}