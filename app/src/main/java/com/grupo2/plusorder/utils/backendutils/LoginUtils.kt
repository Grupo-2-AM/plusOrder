package com.grupo2.plusorder.utils.backendutils

import com.grupo2.plusorder.Constants
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.backend.tables.BackendConta
import java.time.LocalDate
import java.util.*

object LoginUtils {

    // Attempt to login and return an Account if successful
    fun TryLogin(textEmail: String, textPassword : String) : Conta?{
        var contaLogin = Conta(textEmail, textPassword)
        return BackendConta.LoginConta(contaLogin)
    }

    // Attempt to register and return the id if successful
    fun TryRegister(textNome: String, textEmail: String, textPassword: String) : UUID? {
        var contaRegister = Conta(UUID.randomUUID(), textNome, Integer(0), textPassword, Integer(1), Constants.EMPTY_GUID, textEmail, null, null)

        // Attempt register
        var contaAttempt = BackendConta.AddConta(contaRegister)

        // if Added
        return if (contaAttempt)
            contaRegister.id
        else
            null
    }

    // Attempt to register and return the id if successful
    fun TryRegisterExtraInfo(textNome: String, textEmail: String, textPassword: String, idCidade: UUID, NIF: String, dataNasc: LocalDate) : UUID? {
        var contaRegister = Conta(UUID.randomUUID(), textNome, Integer(0), textPassword, Integer(1), idCidade, textEmail, NIF, dataNasc)

        // Attempt register
        var contaAttempt = BackendConta.AddConta(contaRegister)

        // if Added
        return if (contaAttempt)
            contaRegister.id
        else
            null
    }
}