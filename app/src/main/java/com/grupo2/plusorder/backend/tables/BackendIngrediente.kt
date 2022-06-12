package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Cidade
import com.grupo2.plusorder.backend.models.Ingrediente
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendIngrediente {
    private const val BASE_EXTENSION = "Ingrediente/"
    private const val INGREDIENTE_FROM_ID_PRATO = "getAllIngredientesFromPrato/" // + idPrato

    fun GetAllIngredientes() : List<Ingrediente> {
        var ingredientes = arrayListOf<Ingrediente>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var ingredienteJSON = resultArray[index] as JSONObject
                var ingrediente = Ingrediente.fromJSON(ingredienteJSON)
                ingredientes.add(ingrediente)
            }
        }

        return ingredientes
    }

    fun GetAllIngredientesFromIdPrato(idPrato: UUID) : List<Ingrediente> {
        var ingredientes = arrayListOf<Ingrediente>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + INGREDIENTE_FROM_ID_PRATO + idPrato)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var ingredienteJSON = resultArray[index] as JSONObject
                var ingrediente = Ingrediente.fromJSON(ingredienteJSON)
                ingredientes.add(ingrediente)
            }
        }

        return ingredientes
    }

    fun GetIngrediente(id: UUID) : Ingrediente? {
        var ingrediente: Ingrediente? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            ingrediente = Ingrediente.fromJSON(resultJSONObject)
        }

        return ingrediente
    }

    // Adds object to database and returns true if successful
    fun AddIngrediente(ingrediente: Ingrediente) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, ingrediente.toJSON().toString())

        var ingredienteAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            ingredienteAdded = status == "ok"
        }

        return ingredienteAdded
    }

    fun UpdateIngrediente(id: UUID, ingrediente: Ingrediente) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, ingrediente.toJSON().toString())

        var ingredienteUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            ingredienteUpdated = status == "ok"
        }

        return ingredienteUpdated
    }

    fun DeleteIngrediente(id: UUID) : Boolean {
        var IngredienteDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            IngredienteDeleted = status == "ok"
        }

        return IngredienteDeleted
    }
}