package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Conta {

    // Attributes
    var id: UUID? = null
    var nome_pp: String? = null
    var eFuncionario: Byte? = null
    var pass: String? = null
    var ativo: Byte? = null
    var idCidade: UUID? = null
    var email: String? = null
    var NIF: String? = null
    var dataNasc: Date? = null

    // Constructors
    constructor(
        id: UUID?,
        nome_pp: String?,
        eFuncionario: Byte?,
        pass: String?,
        ativo: Byte?,
        idCidade: UUID?,
        email: String?,
        NIF: String?,
        dataNasc: Date?
    ) {
        this.id = id
        this.nome_pp = nome_pp
        this.eFuncionario = eFuncionario
        this.pass = pass
        this.ativo = ativo
        this.idCidade = idCidade
        this.email = email
        this.NIF = NIF
        this.dataNasc = dataNasc
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("nome_pp", nome_pp)
            .put("eFuncionario", eFuncionario)
            .put("pass", pass)
            .put("ativo", ativo)
            .put("idCidade", idCidade)
            .put("email", email)
            .put("NIF", NIF)
            .put("dataNasc", dataNasc);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Conta {
            return Conta(
                jsonObject["id"] as? UUID,
                jsonObject["nome_pp"] as? String,
                jsonObject["eFuncionario"] as? Byte,
                jsonObject["pass"] as? String,
                jsonObject["ativo"] as? Byte,
                jsonObject["idCidade"] as? UUID?,
                jsonObject["email"] as? String,
                jsonObject["NIF"] as? String?,
                jsonObject["dataNasc"] as? Date?
            )
        }
    }
}