package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class TipoPagamento {

    // Attributes
    var id: UUID? = null
    var descricao: String? = null
    var ativo: Integer? = null

    // Constructors
    constructor(id: UUID?, descricao: String?, ativo: Integer?) {
        this.id = id
        this.descricao = descricao
        this.ativo = ativo
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("descricao", descricao)
            .put("ativo", ativo);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): TipoPagamento {
            return TipoPagamento(
                UUID.fromString(jsonObject["id"] as? String),
                jsonObject["descricao"] as? String,
                jsonObject["ativo"] as? Integer
            )
        }
    }
}