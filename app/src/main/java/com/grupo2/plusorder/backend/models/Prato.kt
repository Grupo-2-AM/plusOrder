package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Prato {

    // Attributes
    var id: UUID? = null
    var nome: String? = null
    var tempoEstimado: Double? = null
    var preco: Double? = null
    var ativo: Integer? = null
    var idCategoria: UUID? = null
    var imagem: String? = null
    var descricao: String? = null
    
    // Constructors
    constructor(
        id: UUID?,
        nome: String?,
        tempoEstimado: Double?,
        preco: Double?,
        ativo: Integer?,
        idCategoria: UUID?,
        imagem: String?,
        descricao: String?
    ) {
        this.id = id
        this.nome = nome
        this.tempoEstimado = tempoEstimado
        this.preco = preco
        this.ativo = ativo
        this.idCategoria = idCategoria
        this.imagem = imagem
        this.descricao = descricao
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("nome", nome)
            .put("tempoEstimado", tempoEstimado)
            .put("preco", preco)
            .put("ativo", ativo)
            .put("idCategoria", idCategoria)
            .put("imagem", imagem)
            .put("descricao", descricao);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Prato {
            return Prato(
                UUID.fromString(jsonObject["id"] as? String),
                jsonObject["nome"] as? String,
                jsonObject["tempoEstimado"] as? Double,
                jsonObject["preco"] as? Double,
                jsonObject["ativo"] as? Integer,
                UUID.fromString(jsonObject["idCategoria"] as? String?),
                jsonObject["imagem"] as? String,
                jsonObject["descricao"] as? String
            )
        }
    }
}