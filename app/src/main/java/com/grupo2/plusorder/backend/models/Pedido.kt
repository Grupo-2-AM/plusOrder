package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Pedido {

    // Attributes
    var id: UUID? = null
    var idMesa: UUID? = null
    var idFunc: UUID? = null
    var idCliente: UUID? = null
    var ativo: Integer? = null

    // Constructors
    constructor(id: UUID?, idMesa: UUID?, idFunc: UUID?, idCliente: UUID?, ativo: Integer?) {
        this.id = id
        this.idMesa = idMesa
        this.idFunc = idFunc
        this.idCliente = idCliente
        this.ativo = ativo
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("idMesa", idMesa)
            .put("idFunc", idFunc)
            .put("idCliente", idCliente)
            .put("ativo", ativo);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Pedido {
            return Pedido(
                UUID.fromString(jsonObject["id"] as? String),
                UUID.fromString(jsonObject["idMesa"] as? String),
                UUID.fromString(jsonObject["idFunc"] as? String),
                UUID.fromString(jsonObject["idCliente"] as? String),
                jsonObject["ativo"] as? Integer
            )
        }
    }
}