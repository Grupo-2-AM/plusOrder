package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend
import com.grupo2.plusorder.backend.models.Pagamento
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendPagamento {
    private const val BASE_EXTENSION = "Pagamento/"

    fun GetAllPagamentos() : List<Pagamento> {
        var pagamentos = arrayListOf<Pagamento>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var pagamentoJSON = resultArray[index] as JSONObject
                var avaliacao = Pagamento.fromJSON(pagamentoJSON)
                pagamentos.add(avaliacao)
            }
        }

        return pagamentos
    }

    fun GetPagamento(id: UUID) : Pagamento? {
        var pagamento: Pagamento? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            pagamento = Pagamento.fromJSON(resultJSONObject)
        }

        return pagamento
    }

    // Adds object to database and returns true if successful
    fun AddPagamento(pagamento: Pagamento) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pagamento.toJSON().toString())

        var pagamentoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pagamentoAdded = status == "ok"
        }

        return pagamentoAdded
    }

    fun UpdatePagamento(id: UUID, pagamento: Pagamento) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pagamento.toJSON().toString())

        var pagamentoUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pagamentoUpdated = status == "ok"
        }

        return pagamentoUpdated
    }

    fun DeletePagamento(id: UUID) : Boolean {
        var pagamentoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pagamentoDeleted = status == "ok"
        }

        return pagamentoDeleted
    }
}