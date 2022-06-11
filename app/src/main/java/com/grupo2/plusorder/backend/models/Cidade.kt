package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Cidade {
    // Attributes
    var id: UUID? = null
    var cidade: String? = null

    // Constructors
    constructor(id: UUID?, cidade: String?) {
        this.id = id
        this.cidade = cidade
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("cidade", cidade);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Cidade {
            return Cidade(
                UUID.fromString(jsonObject["id"] as? String),
                jsonObject["cidade"] as? String
            )
        }
    }
}