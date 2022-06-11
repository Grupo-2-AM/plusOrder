package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend
import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Categoria
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendCategoria {

    private const val BASE_EXTENSION = "Categoria/"

    fun GetAllCategorias() : List<Categoria> {
        var categorias = arrayListOf<Categoria>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var contaJSON = resultArray[index] as JSONObject
                var categoria = Categoria.fromJSON(contaJSON)
                categorias.add(categoria)
            }
        }

        return categorias
    }

    fun GetCategoria(id: UUID) : Categoria? {
        var categoria: Categoria? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            categoria = Categoria.fromJSON(resultJSONObject)
        }

        return categoria
    }

    // Adds object to database and returns true if successful
    fun AddCategoria(categoria: Categoria) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, categoria.toJSON().toString())

        var categoriaAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            categoriaAdded = status == "ok"
        }

        return categoriaAdded
    }

    fun UpdateCategoria(id: UUID, categoria: Categoria) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, categoria.toJSON().toString())

        var categoriaUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            categoriaUpdated = status == "ok"
        }

        return categoriaUpdated
    }

    fun DeleteCategoria(id: UUID) : Boolean {
        var categoriaDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            categoriaDeleted = status == "ok"
        }

        return categoriaDeleted
    }
}