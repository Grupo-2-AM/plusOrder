package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend
import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Cidade
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendCidade {
    private const val BASE_EXTENSION = "Cidade/"
    private const val CIDADE_BY_NAME_EXTENSION = "getCidadeByName/"

    fun GetAllCidades() : List<Cidade> {
        var cidades = arrayListOf<Cidade>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var cidadeJSON = resultArray[index] as JSONObject
                var cidade = Cidade.fromJSON(cidadeJSON)
                cidades.add(cidade)
            }
        }

        return cidades
    }

    fun GetCidade(id: UUID) : Cidade? {
        var cidade: Cidade? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            cidade = Cidade.fromJSON(resultJSONObject)
        }

        return cidade
    }

    fun GetCidadeNomeById(id: UUID) : String? {
        var cidadeNome: String? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            cidadeNome = Cidade.fromJSON(resultJSONObject).cidade
        }

        return cidadeNome
    }

    fun GetCidadeByName(name: String) : Cidade? {
        var cidade: Cidade? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + CIDADE_BY_NAME_EXTENSION + name)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            cidade = Cidade.fromJSON(resultJSONObject)
        }

        return cidade
    }

    fun GetCidadeIdByName(name: String) : UUID? {
        var cidadeId: UUID? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + CIDADE_BY_NAME_EXTENSION + name)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            cidadeId = Cidade.fromJSON(resultJSONObject).id
        }

        return cidadeId
    }

    // Adds object to database and returns true if successful
    fun AddCidade(cidade: Cidade) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, cidade.toJSON().toString())

        var cidadeAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            cidadeAdded = status == "ok"
        }

        return cidadeAdded
    }

    fun UpdateCidade(id: UUID, cidade: Cidade) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, cidade.toJSON().toString())

        var cidadeUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            cidadeUpdated = status == "ok"
        }

        return cidadeUpdated
    }

    fun DeleteCidade(id: UUID) : Boolean {
        var cidadeDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            cidadeDeleted = status == "ok"
        }

        return cidadeDeleted
    }
}