package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.TipoPagamento
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

object BackendTipoPagamento {
    private const val BASE_EXTENSION = "TipoPagamento/"

    fun GetAllTipoPagamentos() : List<TipoPagamento> {
        var tipoPagamentos = arrayListOf<TipoPagamento>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var tipoPagamentoJSON = resultArray[index] as JSONObject
                var tipoPagamento = TipoPagamento.fromJSON(tipoPagamentoJSON)
                tipoPagamentos.add(tipoPagamento)
            }
        }

        return tipoPagamentos
    }

    fun GetTipoPagamento(id: UUID) : TipoPagamento? {
        var tipoPagamento: TipoPagamento? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            tipoPagamento = TipoPagamento.fromJSON(resultJSONObject)
        }

        return tipoPagamento
    }

    // Adds object to database and returns true if successful
    fun AddTipoPagamento(tipoPagamento: TipoPagamento) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, tipoPagamento.toJSON().toString())

        var tipoPagamentoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            tipoPagamentoAdded = status == "ok"
        }

        return tipoPagamentoAdded
    }

    fun UpdateTipoPagamento(id: UUID, tipoPagamento: TipoPagamento) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, tipoPagamento.toJSON().toString())

        var tipoPagamentooUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            tipoPagamentooUpdated = status == "ok"
        }

        return tipoPagamentooUpdated
    }

    fun DeleteTipoPagamento(id: UUID) : Boolean {
        var tipoPagamentoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            tipoPagamentoDeleted = status == "ok"
        }

        return tipoPagamentoDeleted
    }
}