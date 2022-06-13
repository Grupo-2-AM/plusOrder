package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Pagamento {

    // Attributes
    var id: UUID? = null
    var idTipo: UUID? = null
    var idPedido: UUID? = null
    var dataPag: LocalDate? = null

    // Constructors
    constructor(id: UUID?, idTipo: UUID?, idPedido: UUID?, dataPag: LocalDate?) {
        this.id = id
        this.idTipo = idTipo
        this.idPedido = idPedido
        this.dataPag = dataPag
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("idTipo", idTipo)
            .put("idPedido", idPedido)
            .put("dataPag", dataPag);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Pagamento {
            return Pagamento(
                UUID.fromString(jsonObject["id"] as? String),
                UUID.fromString(jsonObject["idTipo"] as? String),
                UUID.fromString(jsonObject["idPedido"] as? String),
                LocalDate.parse(jsonObject["dataPag"] as? String?, DateTimeFormatter.ISO_DATE_TIME)
            )
        }
    }
}