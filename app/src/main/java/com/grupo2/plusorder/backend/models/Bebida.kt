package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Bebida {

    // Attributes
    var id: UUID? = null
    var nome: String? = null
    var preco: Double? = null
    var litros: Double? = null
    var ativo: Integer? = null
    var imagem: String? = null

    // Constructors
    constructor(
        id: UUID?,
        nome: String?,
        preco: Double?,
        litros: Double?,
        ativo: Integer?,
        imagem: String?
    ) {
        this.id = id
        this.nome = nome
        this.preco = preco
        this.litros = litros
        this.ativo = ativo
        this.imagem = imagem
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("nome", nome)
            .put("preco", preco)
            .put("litros", litros)
            .put("imagem", imagem);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Bebida {
            return Bebida(
                UUID.fromString(jsonObject["id"] as? String),
                jsonObject["nome"] as? String,
                jsonObject["preco"] as? Double,
                jsonObject["litros"] as? Double,
                jsonObject["ativo"] as? Integer,
                jsonObject["imagem"] as? String
            )
        }
    }
}