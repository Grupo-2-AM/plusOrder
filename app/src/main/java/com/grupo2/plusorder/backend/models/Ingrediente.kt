package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Ingrediente {

    // Attributes
    var id: UUID? = null
    var nome: String? = null

    // Constructors
    constructor(id: UUID?, nome: String?) {
        this.id = id
        this.nome = nome
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("nome", nome);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Ingrediente {
            return Ingrediente(
                UUID.fromString(jsonObject["id"] as? String),
                jsonObject["nome"] as? String
            )
        }
    }
}