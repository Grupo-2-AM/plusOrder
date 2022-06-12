package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend
import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Categoria
import com.grupo2.plusorder.backend.models.Cidade
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

object BackendCategoria {
    private const val BASE_EXTENSION = "Categoria/"
    private const val CATEGORIA_BY_NAME_EXTENSION = "getCategoriaByName/"

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
                var categoriaJSON = resultArray[index] as JSONObject
                var categoria = Categoria.fromJSON(categoriaJSON)
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

    fun GetNameCategoriaById(id: UUID) : String? {
        var categoriaNome: String? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Backend.BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            categoriaNome = Categoria.fromJSON(resultJSONObject).categoria
        }

        return categoriaNome
    }

    fun GetCategoriaByName(name: String) : Categoria? {
        var categoria: Categoria? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + CATEGORIA_BY_NAME_EXTENSION + name)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            categoria = Categoria.fromJSON(resultJSONObject)
        }

        return categoria
    }

    fun GetCategoriaIdByName(name: String) : UUID? {
        var categoriaId: UUID? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + CATEGORIA_BY_NAME_EXTENSION + name)
            .build()

        var countDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful)
                        throw IOException("Unexpected code $response")

                    if (response.body != null){
                        var result = response.body!!.string()

                        var resultJSONObject = JSONObject(result)
                        categoriaId = Categoria.fromJSON(resultJSONObject).id
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return categoriaId
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