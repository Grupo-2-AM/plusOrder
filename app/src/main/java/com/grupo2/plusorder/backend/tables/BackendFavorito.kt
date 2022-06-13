package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Favorito
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendFavorito {
    private const val BASE_EXTENSION = "Favorito/"

    fun GetAllFavoritos() : List<Favorito> {
        var favoritos = arrayListOf<Favorito>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var favoritoJSON = resultArray[index] as JSONObject
                var favorito = Favorito.fromJSON(favoritoJSON)
                favoritos.add(favorito)
            }
        }

        return favoritos
    }

    fun GetFavorito(id: UUID) : Favorito? {
        var favorito: Favorito? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            favorito = Favorito.fromJSON(resultJSONObject)
        }

        return favorito
    }

    // Adds object to database and returns true if successful
    fun AddFavorito(favorito: Favorito) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, favorito.toJSON().toString())

        var favoritoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            favoritoAdded = status == "ok"
        }

        return favoritoAdded
    }

    fun UpdateFavorito(id: UUID, favorito: Favorito) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, favorito.toJSON().toString())

        var favoritoUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            favoritoUpdated = status == "ok"
        }

        return favoritoUpdated
    }

    fun DeleteFavorito(id: UUID) : Boolean {
        var favoritoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            favoritoDeleted = status == "ok"
        }

        return favoritoDeleted
    }
}