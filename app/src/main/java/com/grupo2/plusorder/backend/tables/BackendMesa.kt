package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend
import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Mesa
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendMesa {
    private const val BASE_EXTENSION = "Mesa/"

    fun GetAllMesas() : List<Mesa> {
        var mesas = arrayListOf<Mesa>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var mesaJSON = resultArray[index] as JSONObject
                var mesa = Mesa.fromJSON(mesaJSON)
                mesas.add(mesa)
            }
        }

        return mesas
    }

    fun GetMesa(id: UUID) : Mesa? {
        var mesa: Mesa? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            mesa = Mesa.fromJSON(resultJSONObject)
        }

        return mesa
    }

    // Adds object to database and returns true if successful
    fun AddMesa(mesa: Mesa) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, mesa.toJSON().toString())

        var mesaAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            mesaAdded = status == "ok"
        }

        return mesaAdded
    }

    fun UpdateMesa(id: UUID, mesa: Mesa) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, mesa.toJSON().toString())

        var mesaUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            mesaUpdated = status == "ok"
        }

        return mesaUpdated
    }

    fun DeleteMesa(id: UUID) : Boolean {
        var mesaDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            mesaDeleted = status == "ok"
        }

        return mesaDeleted
    }

}