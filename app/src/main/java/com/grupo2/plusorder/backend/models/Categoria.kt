package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Categoria {

    // Attributes
    var id: UUID? = null
    var categoria: String? = null

    // Constructors
    constructor(id: UUID?, categoria: String?) {
        this.id = id
        this.categoria = categoria
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("categoria", categoria);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Categoria {
            return Categoria(
                UUID.fromString(jsonObject["id"] as? String),
                jsonObject["categoria"] as? String
            )
        }
    }
}