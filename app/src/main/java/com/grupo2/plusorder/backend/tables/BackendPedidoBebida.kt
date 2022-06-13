package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.PedidoBebida
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendPedidoBebida {
    private const val BASE_EXTENSION = "PedidoBebida/"

    fun GetAllPedidoBebidas() : List<PedidoBebida> {
        var pedidoBebidas = arrayListOf<PedidoBebida>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var pedidoBebidaJSON = resultArray[index] as JSONObject
                var pedidoBebida = PedidoBebida.fromJSON(pedidoBebidaJSON)
                pedidoBebidas.add(pedidoBebida)
            }
        }

        return pedidoBebidas
    }

    fun GetPedidoBebida(id: UUID) : PedidoBebida? {
        var pedidoBebida: PedidoBebida? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            pedidoBebida = PedidoBebida.fromJSON(resultJSONObject)
        }

        return pedidoBebida
    }

    // Adds object to database and returns true if successful
    fun AddPedidoBebida(pedidoBebida: PedidoBebida) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pedidoBebida.toJSON().toString())

        var pedidoBebidaAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoBebidaAdded = status == "ok"
        }

        return pedidoBebidaAdded
    }

    fun UpdatePedidoBebida(id: UUID, pedidoBebida: PedidoBebida) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pedidoBebida.toJSON().toString())

        var pedidoBebidaUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoBebidaUpdated = status == "ok"
        }

        return pedidoBebidaUpdated
    }

    fun DeletePedidoBebida(id: UUID) : Boolean {
        var pedidoBebidaDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoBebidaDeleted = status == "ok"
        }

        return pedidoBebidaDeleted
    }
}