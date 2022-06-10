package com.grupo2.plusorder

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.utils.AppContext
import com.grupo2.plusorder.utils.backendutils.LoginUtils
import com.grupo2.plusorder.utils.uiutils.AlertDialogUtils
import kotlinx.coroutines.delay

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        (findViewById<View>(R.id.buttonLogin) as Button).setOnClickListener {

            var loggedConta : Conta? = LoginUtils.TryLogin(
                findViewById<TextInputEditText>(R.id.editTextEmail).text.toString(),
                findViewById<TextInputEditText>(R.id.editTextPassword).text.toString())

            if (loggedConta == null) {
                AlertDialogUtils.ShowOkAlertBox("Credenciais incorretas", "Tenete novamente.", this)
            } else {
                AlertDialogUtils.ShowOkAlertBox("Bem Vindo", loggedConta.nome_pp.toString(), this)
            }
        }
    }
}