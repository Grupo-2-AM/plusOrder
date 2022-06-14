package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.IngredientePrato
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendIngredientePrato {
    private const val BASE_EXTENSION = "IngredientePrato/"

    fun GetAllIngredientePratos() : List<IngredientePrato> {
        var ingredientePratos = arrayListOf<IngredientePrato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var ingredientePratoJSON = resultArray[index] as JSONObject
                var ingredientePrato = IngredientePrato.fromJSON(ingredientePratoJSON)
                ingredientePratos.add(ingredientePrato)
            }
        }

        return ingredientePratos
    }

    fun GetIngredientePrato(id: UUID) : IngredientePrato? {
        var ingredientePrato: IngredientePrato? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            ingredientePrato = IngredientePrato.fromJSON(resultJSONObject)
        }

        return ingredientePrato
    }

    // Adds object to database and returns true if successful
    fun AddIngredientePrato(ingredientePrato: IngredientePrato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, ingredientePrato.toJSON().toString())

        var ingredientePratoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            ingredientePratoAdded = status == "ok"
        }

        return ingredientePratoAdded
    }

    fun UpdateIngredientePratoAdded(id: UUID, ingredientePrato: IngredientePrato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, ingredientePrato.toJSON().toString())

        var ingredientePratoUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            ingredientePratoUpdated = status == "ok"
        }

        return ingredientePratoUpdated
    }

    fun DeleteIngredientePrato(id: UUID) : Boolean {
        var ingredientePratoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            ingredientePratoDeleted = status == "ok"
        }

        return ingredientePratoDeleted
    }
}