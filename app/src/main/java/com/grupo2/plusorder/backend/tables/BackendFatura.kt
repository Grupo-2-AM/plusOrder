package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Fatura
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendFatura {
    private const val BASE_EXTENSION = "Fatura/"

    fun GetAllFaturas() : List<Fatura> {
        var faturas = arrayListOf<Fatura>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var faturaJSON = resultArray[index] as JSONObject
                var fatura = Fatura.fromJSON(faturaJSON)
                faturas.add(fatura)
            }
        }

        return faturas
    }

    fun GetFatura(id: UUID) : Fatura? {
        var fatura: Fatura? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            fatura = Fatura.fromJSON(resultJSONObject)
        }

        return fatura
    }

    // Adds object to database and returns true if successful
    fun AddFatura(fatura: Fatura) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, fatura.toJSON().toString())

        var faturaAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            faturaAdded = status == "ok"
        }

        return faturaAdded
    }

    fun UpdateFatura(id: UUID, fatura: Fatura) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, fatura.toJSON().toString())

        var faturaUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            faturaUpdated = status == "ok"
        }

        return faturaUpdated
    }

    fun DeleteFatura(id: UUID) : Boolean {
        var faturaDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            faturaDeleted = status == "ok"
        }

        return faturaDeleted
    }
}