package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Conta {

    // Attributes
    var id: UUID? = null
    var nome_pp: String? = null
    var eFuncionario: Integer? = null
    var pass: String? = null
    var ativo: Integer? = null
    var idCidade: UUID? = null
    var email: String? = null
    var NIF: String? = null
    var dataNasc: LocalDate? = null
    var imagem: String? = null

    // Constructors
    constructor(email: String?, pass: String?) {
        this.pass = pass
        this.email = email
    }

    constructor(
        id: UUID?,
        nome_pp: String?,
        eFuncionario: Integer?,
        pass: String?,
        ativo: Integer?,
        idCidade: UUID?,
        email: String?,
        NIF: String?,
        dataNasc: LocalDate?,
        imagem: String?
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
        this.imagem = imagem
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
            .put("nif", NIF)
            .put("dataNasc", dataNasc)
            .put("imagem", imagem);
    }

    fun isFuncionario(): Boolean {
        return eFuncionario == Integer(1)
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Conta {
            // Handle null date
            if (jsonObject["dataNasc"] as? String? == null)
                return Conta(
                    UUID.fromString(jsonObject["id"] as? String),
                    jsonObject["nome_pp"] as? String,
                    jsonObject["eFuncionario"] as? Integer,
                    jsonObject["pass"] as? String,
                    jsonObject["ativo"] as? Integer,
                    UUID.fromString(jsonObject["idCidade"] as? String?),
                    jsonObject["email"] as? String,
                    jsonObject["nif"] as? String?,
                    LocalDate.now(),
                    jsonObject["imagem"] as? String
                )
            else
                return Conta(
                    UUID.fromString(jsonObject["id"] as? String),
                    jsonObject["nome_pp"] as? String,
                    jsonObject["eFuncionario"] as? Integer,
                    jsonObject["pass"] as? String,
                    jsonObject["ativo"] as? Integer,
                    UUID.fromString(jsonObject["idCidade"] as? String?),
                    jsonObject["email"] as? String,
                    jsonObject["nif"] as? String?,
                    LocalDate.parse(jsonObject["dataNasc"] as? String?, DateTimeFormatter.ISO_DATE_TIME),
                    jsonObject["imagem"] as? String
                )
        }
    }
}