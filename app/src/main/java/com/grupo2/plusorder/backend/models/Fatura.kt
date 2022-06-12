package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Fatura {

    // Attributes
    var id: UUID? = null
    var idPagamento: UUID? = null
    var fatura: ByteArray? = null
    var ativo: Integer? = null

    // Constructors
    constructor(id: UUID?, idPagamento: UUID?, fatura: ByteArray?, ativo: Integer?) {
        this.id = id
        this.idPagamento = idPagamento
        this.fatura = fatura
        this.ativo = ativo
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("idPagamento", idPagamento)
            .put("fatura", fatura)
            .put("ativo", ativo);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Fatura {
            return Fatura(
                UUID.fromString(jsonObject["id"] as? String),
                UUID.fromString(jsonObject["idPagamento"] as? String),
                jsonObject["fatura"] as? ByteArray?,
                jsonObject["ativo"] as? Integer
            )
        }
    }
}