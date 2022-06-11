package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Bebida
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendBebida {
    private const val BASE_EXTENSION = "Bebida/"

    fun GetAllBebidas() : List<Bebida> {
        var bebidas = arrayListOf<Bebida>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var bebidasJSON = resultArray[index] as JSONObject
                var bebida = Bebida.fromJSON(bebidasJSON)
                bebidas.add(bebida)
            }
        }

        return bebidas
    }

    fun GetBebida(id: UUID) : Bebida? {
        var bebida: Bebida? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            bebida = Bebida.fromJSON(resultJSONObject)
        }

        return bebida
    }

    // Adds object to database and returns true if successful
    fun AddBebida(bebida: Bebida) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, bebida.toJSON().toString())

        var bebidaAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            bebidaAdded = status == "ok"
        }

        return bebidaAdded
    }

    fun UpdateBebida(id: UUID, bebida: Bebida) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, bebida.toJSON().toString())

        var bebidaUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            bebidaUpdated = status == "ok"
        }

        return bebidaUpdated
    }

    fun DeleteBebida(id: UUID) : Boolean {
        var bebidaDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            bebidaDeleted = status == "ok"
        }

        return bebidaDeleted
    }
}