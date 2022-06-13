package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class PedidoBebida {

    // Attributes
    var idBebida: UUID? = null
    var idPedido: UUID? = null
    var quantidade: Integer? = null
    var precoUnidade: Double? = null

    // Constructors
    constructor(idBebida: UUID?, idPedido: UUID?, quantidade: Integer?, precoUnidade: Double?) {
        this.idBebida = idBebida
        this.idPedido = idPedido
        this.quantidade = quantidade
        this.precoUnidade = precoUnidade
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("idBebida", idBebida)
            .put("idPedido", idPedido)
            .put("quantidade", quantidade)
            .put("precoUnidade", precoUnidade);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): PedidoBebida {
            return PedidoBebida(
                UUID.fromString(jsonObject["idBebida"] as? String),
                UUID.fromString(jsonObject["idPedido"] as? String),
                jsonObject["quantidade"] as? Integer,
                jsonObject["precoUnidade"] as? Double
            )
        }
    }
}