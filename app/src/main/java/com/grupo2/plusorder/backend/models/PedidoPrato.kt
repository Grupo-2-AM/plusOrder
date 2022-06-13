package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PedidoPrato {

    // Attributes
    var idPrato: UUID? = null
    var idPedido: UUID? = null
    var quantidade: Double? = null
    var data: LocalDate? = null

    // Constructors
    constructor(idPrato: UUID?, idPedido: UUID?, quantidade: Double?, data: LocalDate?) {
        this.idPrato = idPrato
        this.idPedido = idPedido
        this.quantidade = quantidade
        this.data = data
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("idPrato", idPrato)
            .put("idPedido", idPedido)
            .put("quantidade", quantidade)
            .put("data", data);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): PedidoPrato {
            return PedidoPrato(
                UUID.fromString(jsonObject["idPrato"] as? String),
                UUID.fromString(jsonObject["idPedido"] as? String),
                jsonObject["quantidade"] as? Double,
                LocalDate.parse(jsonObject["data"] as? String?, DateTimeFormatter.ISO_DATE_TIME)
            )
        }
    }
}