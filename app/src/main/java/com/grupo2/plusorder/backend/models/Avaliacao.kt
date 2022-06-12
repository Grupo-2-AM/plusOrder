package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Avaliacao {

    // Attributes
    var id: UUID? = null
    var idPrato: UUID? = null
    var idCliente: UUID? = null
    var avaliacao: Double? = null
    var data: LocalDate? = null

    // Constructors
    constructor(id: UUID?, idPrato: UUID?, idCliente: UUID?, avaliacao: Double?, data: LocalDate?) {
        this.id = id
        this.idPrato = idPrato
        this.idCliente = idCliente
        this.avaliacao = avaliacao
        this.data = data
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("idPrato", idPrato)
            .put("idCliente", idCliente)
            .put("avaliacao", avaliacao)
            .put("data", data);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Avaliacao {
            return Avaliacao(
                UUID.fromString(jsonObject["id"] as? String),
                UUID.fromString(jsonObject["idPrato"] as? String),
                UUID.fromString(jsonObject["idCliente"] as? String),
                jsonObject["avaliacao"] as? Double,
                LocalDate.parse(jsonObject["data"] as? String?, DateTimeFormatter.ISO_DATE_TIME)
            )
        }
    }
}