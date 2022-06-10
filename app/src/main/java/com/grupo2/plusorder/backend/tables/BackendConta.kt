package com.grupo2.plusorder.backend.tables

import android.os.Handler
import android.os.Looper.getMainLooper
import android.widget.Toast
import com.grupo2.plusorder.LoginPage
import com.grupo2.plusorder.MainActivity
import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.utils.AppContext
import com.grupo2.plusorder.utils.DateUtils
import com.grupo2.plusorder.utils.uiutils.AlertDialogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

object BackendConta {

    const val BASE_EXTENSION = "Conta/"
    const val LOGIN_EXTENSION = "postLogin/"

    fun GetAllContas(callback : ((List<Conta>)->Unit)) {
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

    fun GetConta(id: UUID, callback : ((Conta)->Unit)) {
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + BASE_EXTENSION + id)
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

    fun AddConta(conta: Conta, callback : ((Boolean)->Unit)) {
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

    fun UpdateConta(id: UUID, conta: Conta, callback : ((Boolean)->Unit)) {
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

    fun deleteConta(id: UUID, callback : ((Boolean)->Unit)) {
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

    fun LoginConta(contaLogin: Conta) : Conta? {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, contaLogin.toJSON().toString())

        var conta : Conta? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + LOGIN_EXTENSION)
            .post(body)
            .build();

        var countDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //AlertDialogUtils.ShowOkAlertBox("Não conseguimos conectar à Base de Dados", "Tente novamente.", AppContext.appContext)
                e.printStackTrace()
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful)
                        throw IOException("Unexpected code $response")

                    var result = response.body!!.string()
                    // Check if result has values
                    if (result != ""){
                        var resultJSONObject = JSONObject(result)
                        conta = Conta.fromJSON(resultJSONObject)
                    }

                    countDownLatch.countDown()
                }
            }
        })
        // await until post request finished
        countDownLatch.await()

        return conta
    }

    fun GetAge(conta: Conta) : Int? {
        // Check if conta.dataNasc filled
        if (conta.dataNasc != null)
            return DateUtils.GetAge(conta.dataNasc!!)
        return null
    }
}