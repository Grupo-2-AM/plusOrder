package com.grupo2.plusorder.utils.backendutils

import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.backend.tables.BackendConta

object LoginUtils {

    // Attempt to login and return an Account if successful
    fun TryLogin(textEmail: String, textPassword : String) : Conta?{
        var contaLogin = Conta(textEmail, textPassword)
        return BackendConta.LoginConta(contaLogin)
    }

}