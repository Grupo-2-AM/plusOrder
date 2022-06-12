package com.grupo2.plusorder.backend.models

import org.json.JSONObject
import java.util.*

class Mesa {

    // Attributes
    var id: UUID? = null
    var nrMesa: Integer? = null
    var qrImagem: ByteArray? = null
    var ativo: Integer? = null

    // Constructors
    constructor(id: UUID?, nrMesa: Integer?, qrImagem: ByteArray?, ativo: Integer?) {
        this.id = id
        this.nrMesa = nrMesa
        this.qrImagem = qrImagem
        this.ativo = ativo
    }

    // Functions
    fun toJSON(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("nrMesa", nrMesa)
            .put("qrImagem", qrImagem)
            .put("ativo", ativo);
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject): Mesa {
            return Mesa(
                UUID.fromString(jsonObject["id"] as? String),
                jsonObject["nrMesa"] as? Integer,
                (jsonObject["qrImagem"] as? String)!!.toByteArray(),
                jsonObject["ativo"] as? Integer
            )
        }

        val QR_CODE : String = "plusOrder" // + nrMesa
    }
}