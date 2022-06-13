package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Pedido
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object BackendPedido {
    private const val BASE_EXTENSION = "Pedido/"

    fun GetAllPedidos() : List<Pedido> {
        var pedidos = arrayListOf<Pedido>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultArray = JSONArray(result)

            for (index in 0 until resultArray.length()) {
                var pedidoJSON = resultArray[index] as JSONObject
                var pedido = Pedido.fromJSON(pedidoJSON)
                pedidos.add(pedido)
            }
        }

        return pedidos
    }

    fun GetPedido(id: UUID) : Pedido? {
        var pedido: Pedido? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)
            pedido = Pedido.fromJSON(resultJSONObject)
        }

        return pedido
    }

    // Adds object to database and returns true if successful
    fun AddPedido(pedido: Pedido) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pedido.toJSON().toString())

        var pedidoAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoAdded = status == "ok"
        }

        return pedidoAdded
    }

    fun UpdatePedido(id: UUID, pedido: Pedido) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, pedido.toJSON().toString())

        var pedidoUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoUpdated = status == "ok"
        }

        return pedidoUpdated
    }

    fun DeletePedido(id: UUID) : Boolean {
        var pedidoDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            var result = response.body!!.string()
            var resultJSONObject = JSONObject(result)

            val status = resultJSONObject.getString("status")
            pedidoDeleted = status == "ok"
        }

        return pedidoDeleted
    }

}