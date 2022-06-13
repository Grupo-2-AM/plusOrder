package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Favorito {

    // Attributes
    var id: UUID? = null
    var idPrato: UUID? = null
    var idCliente: UUID? = null

    // Constructors
    constructor(id: UUID?, idPrato: UUID?, idCliente: UUID?) {
        this.id = id
        this.idPrato = idPrato
        this.idCliente = idCliente
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("idPrato", idPrato)
            .put("idCliente", idCliente);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Favorito {
            return Favorito(
                UUID.fromString(jsonObject["id"] as? String),
                UUID.fromString(jsonObject["idPrato"] as? String),
                UUID.fromString(jsonObject["idCliente"] as? String)
            )
        }
    }

}