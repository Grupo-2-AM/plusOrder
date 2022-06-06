package com.grupo2.plusorder

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.utils.backendutils.LoginUtils

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        (findViewById<View>(R.id.buttonLogin) as Button).setOnClickListener {

            var loggedConta : Conta? = LoginUtils.TryLogin(
                findViewById<TextInputEditText>(R.id.editTextEmail).text.toString(),
                findViewById<TextInputEditText>(R.id.editTextPassword).text.toString())

            if (loggedConta == null) {
                Toast.makeText(applicationContext, "NAO", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(applicationContext, loggedConta.id.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}