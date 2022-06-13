package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PedidoPrato {

    // Attributes
    var idPrato: UUID? = null
    var idPedido: UUID? = null
    var quantidade: Integer? = null
    var precoUnit: Double? = null

    // Constructors
    constructor(idPrato: UUID?, idPedido: UUID?, quantidade: Integer?, precoUnit: Double?) {
        this.idPrato = idPrato
        this.idPedido = idPedido
        this.quantidade = quantidade
        this.precoUnit = precoUnit
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("idPrato", idPrato)
            .put("idPedido", idPedido)
            .put("quantidade", quantidade)
            .put("precoUnit", precoUnit);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): PedidoPrato {
            return PedidoPrato(
                UUID.fromString(jsonObject["idPrato"] as? String),
                UUID.fromString(jsonObject["idPedido"] as? String),
                jsonObject["quantidade"] as? Integer,
                jsonObject["precoUnit"] as? Double
            )
        }
    }
}