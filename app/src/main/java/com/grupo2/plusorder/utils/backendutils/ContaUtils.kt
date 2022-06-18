package com.grupo2.plusorder.utils.backendutils

import com.grupo2.plusorder.Constants
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.backend.tables.BackendCidade
import com.grupo2.plusorder.backend.tables.BackendConta
import java.time.LocalDate
import java.util.*

object ContaUtils {

    // Attempt to update account
    fun TryUpdate(idConta: UUID, textNome: String, textPassword: String, textEmail: String, textNif: String?, dataNasc: LocalDate?, idCidade: UUID?): Boolean? {

        var contaUpdate = Conta(
            UUID.randomUUID(),
            textNome,
            Integer(0),
            textPassword,
            Integer(1),
            idCidade,
            textEmail,
            textNif,
            dataNasc,
            Constants.DEFAULT_IMAGEM
        )

        // Attempt update
        var contaAttempt = BackendConta.UpdateConta(idConta, contaUpdate)

        // if updated
        return contaAttempt
    }
}