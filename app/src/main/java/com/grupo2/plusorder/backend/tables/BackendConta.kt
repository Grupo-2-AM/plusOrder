package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Conta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class BackendConta {

    companion object {
        const val BASE_EXTENSION = "Conta"
    }

    fun getAllContas(callback : ((List<Conta>)->Unit)) {
        var contas = arrayListOf<Conta>()
        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + BASE_EXTENSION)
                .build()

            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultArray = JSONArray(result)

                for (index in 0 until resultArray.length()) {
                    var contaJSON = resultArray[index] as JSONObject
                    var conta = Conta.fromJSON(contaJSON)
                    contas.add(conta)
                }

                GlobalScope.launch(Dispatchers.Main) {
                    callback.invoke(contas)
                }
            }
        }
    }

    fun getConta(id: UUID, callback : ((Conta)->Unit)) {
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + BASE_EXTENSION + "/" + id)
                .build()

            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)
                var conta = Conta.fromJSON(resultJSONObject)

                GlobalScope.launch(Dispatchers.Main) {
                    callback.invoke(conta)
                }
            }
        }
    }

    fun addConta(conta: Conta, callback : ((Boolean)->Unit)) {

        GlobalScope.launch (Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body: RequestBody = RequestBody.create(
                mediaType, conta.toJSON().toString())

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + BASE_EXTENSION)
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)

                GlobalScope.launch (Dispatchers.Main){
                    val status = resultJSONObject.getString("status")
                    callback.invoke(status == "ok")
                }
            }
        }
    }

    fun updateConta(id: UUID, conta: Conta, callback : ((Boolean)->Unit)) {

        GlobalScope.launch (Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body: RequestBody = RequestBody.create(
                mediaType, conta.toJSON().toString())

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + BASE_EXTENSION + "/" + id)
                .put(body)
                .build()

            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)

                GlobalScope.launch (Dispatchers.Main){
                    val status = resultJSONObject.getString("status")
                    callback.invoke(status == "ok")
                }
            }
        }
    }

    fun deleteCavalo(id: UUID, callback : ((Boolean)->Unit)) {

        GlobalScope.launch (Dispatchers.IO) {

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + BASE_EXTENSION + "/" + id)
                .delete()
                .build()

            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)

                GlobalScope.launch (Dispatchers.Main){
                    val status = resultJSONObject.getString("status")
                    callback.invoke(status == "ok")
                }
            }
        }
    }

}