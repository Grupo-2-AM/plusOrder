package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Avaliacao
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendAvaliacao {
    private const val BASE_EXTENSION = "Avaliacao/"

    fun GetAllAvaliacoes() : List<Avaliacao> {
        var avaliacoes = arrayListOf<Avaliacao>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var avaliacaoJSON = resultArray[index] as JSONObject
                var avaliacao = Avaliacao.fromJSON(avaliacaoJSON)
                avaliacoes.add(avaliacao)
            }
        }

        return avaliacoes
    }

    fun GetAvaliacao(id: UUID) : Avaliacao? {
        var avaliacao: Avaliacao? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            avaliacao = Avaliacao.fromJSON(resultJSONObject)
        }

        return avaliacao
    }

    // Adds object to database and returns true if successful
    fun AddAvaliacao(avaliacao: Avaliacao) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, avaliacao.toJSON().toString())

        var avaliacaoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            avaliacaoAdded = status == "ok"
        }

        return avaliacaoAdded
    }

    fun UpdateAvaliacao(id: UUID, avaliacao: Avaliacao) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, avaliacao.toJSON().toString())

        var avaliacaoUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            avaliacaoUpdated = status == "ok"
        }

        return avaliacaoUpdated
    }

    fun DeleteAvaliacao(id: UUID) : Boolean {
        var avaliacaoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            avaliacaoDeleted = status == "ok"
        }

        return avaliacaoDeleted
    }
}