package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Prato
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendPrato {

    private const val BASE_EXTENSION = "Prato/"
    private const val CATEGORIA_SEARCH_EXTENSION = "pratoByCategoria/"
    private const val NAME_CATEGORIA_SEARCH_EXTENSION = "searchPratoWithCategoria/"

    fun GetAllPratos() : List<Prato> {
        var pratos = arrayListOf<Prato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var pratoJSON = resultArray[index] as JSONObject
                var prato = Prato.fromJSON(pratoJSON)
                pratos.add(prato)
            }
        }

        return pratos
    }

    fun GetAllPratosByCategoria(idCategoriaSearch: UUID) : List<Prato>{
        var pratos = arrayListOf<Prato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + CATEGORIA_SEARCH_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var pratoJSON = resultArray[index] as JSONObject
                var prato = Prato.fromJSON(pratoJSON)

                pratos.add(prato)
            }
        }

        return pratos
    }

    fun SearchPratosByNameCategoria(nameSearch: String, idCategoriaSearch: UUID) : List<Prato>{
        var pratos = arrayListOf<Prato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + NAME_CATEGORIA_SEARCH_EXTENSION + nameSearch + "/" + idCategoriaSearch)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var pratoJSON = resultArray[index] as JSONObject
                var prato = Prato.fromJSON(pratoJSON)

                pratos.add(prato)
            }
        }

        return pratos
    }

    fun GetPrato(id: UUID) : Prato? {
        var prato: Prato? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            prato = Prato.fromJSON(resultJSONObject)
        }

        return prato
    }

    // Adds object to database and returns true if successful
    fun AddPrato(prato: Prato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, prato.toJSON().toString())

        var pratoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pratoAdded = status == "ok"
        }

        return pratoAdded
    }

    fun UpdatePrato(id: UUID, prato: Prato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, prato.toJSON().toString())

        var pratoUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pratoUpdated = status == "ok"
        }

        return pratoUpdated
    }

    fun DeletePrato(id: UUID) : Boolean {
        var pratoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pratoDeleted = status == "ok"

        }

        return pratoDeleted
    }
}