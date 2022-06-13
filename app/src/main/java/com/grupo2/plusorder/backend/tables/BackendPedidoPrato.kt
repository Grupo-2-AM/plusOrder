package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.PedidoPrato
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendPedidoPrato {
    private const val BASE_EXTENSION = "PedidoPrato/"

    fun GetAllPedidoPratos() : List<PedidoPrato> {
        var pedidoPratos = arrayListOf<PedidoPrato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var pedidoPratosJSON = resultArray[index] as JSONObject
                var pedidoPrato = PedidoPrato.fromJSON(pedidoPratosJSON)
                pedidoPratos.add(pedidoPrato)
            }
        }

        return pedidoPratos
    }

    fun GetPedidoPrato(id: UUID) : PedidoPrato? {
        var pedidoPrato: PedidoPrato? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            pedidoPrato = PedidoPrato.fromJSON(resultJSONObject)
        }

        return pedidoPrato
    }

    // Adds object to database and returns true if successful
    fun AddPedidoPrato(pedidoPrato: PedidoPrato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pedidoPrato.toJSON().toString())

        var pedidoPratoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoPratoAdded = status == "ok"
        }

        return pedidoPratoAdded
    }

    fun UpdatePedidoPrato(id: UUID, pedidoPrato: PedidoPrato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pedidoPrato.toJSON().toString())

        var pedidoPratoUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoPratoUpdated = status == "ok"
        }

        return pedidoPratoUpdated
    }

    fun DeletePedidoPrato(id: UUID) : Boolean {
        var pedidoPratoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoPratoDeleted = status == "ok"
        }

        return pedidoPratoDeleted
    }
}