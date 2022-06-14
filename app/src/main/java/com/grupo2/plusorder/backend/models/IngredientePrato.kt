package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class IngredientePrato {

    // Attributes
    var idIngrediente: UUID? = null
    var idPrato: UUID? = null
    var quantidade: Integer? = null

    // Constructors
    constructor(idIngrediente: UUID?, idPrato: UUID?, quantidade: Integer?) {
        this.idIngrediente = idIngrediente
        this.idPrato = idPrato
        this.quantidade = quantidade
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("idIngrediente", idIngrediente)
            .put("idPrato", idPrato)
            .put("quantidade", quantidade);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): IngredientePrato {
            return IngredientePrato(
                UUID.fromString(jsonObject["idIngrediente"] as? String),
                UUID.fromString(jsonObject["idPrato"] as? String),
                jsonObject["quantidade"] as? Integer
            )
        }
    }
}