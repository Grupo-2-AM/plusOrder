package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Cidade
import com.grupo2.plusorder.backend.models.Ingrediente
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

object BackendIngrediente {
    private const val BASE_EXTENSION = "Ingrediente/"
    private const val INGREDIENTE_FROM_ID_PRATO = "getAllIngredientesFromPrato/" // + idPrato

    fun GetAllIngredientes() : List<Ingrediente> {
        var ingredientes = arrayListOf<Ingrediente>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
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
                        var resultArray = JSONArray(result)

                        for (index in 0 until resultArray.length()) {
                            var ingredienteJSON = resultArray[index] as JSONObject
                            var ingrediente = Ingrediente.fromJSON(ingredienteJSON)
                            ingredientes.add(ingrediente)
                        }
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return ingredientes
    }

    fun GetAllIngredientesFromIdPrato(idPrato: UUID) : List<Ingrediente> {
        var ingredientes = arrayListOf<Ingrediente>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + INGREDIENTE_FROM_ID_PRATO + idPrato)
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
                        var resultArray = JSONArray(result)

                        for (index in 0 until resultArray.length()) {
                            var ingredienteJSON = resultArray[index] as JSONObject
                            var ingrediente = Ingrediente.fromJSON(ingredienteJSON)
                            ingredientes.add(ingrediente)
                        }
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return ingredientes
    }

    fun GetIngrediente(id: UUID) : Ingrediente? {
        var ingrediente: Ingrediente? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
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
                        ingrediente = Ingrediente.fromJSON(resultJSONObject)
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return ingrediente
    }

    // Adds object to database and returns true if successful
    fun AddIngrediente(ingrediente: Ingrediente) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, ingrediente.toJSON().toString())

        var ingredienteAdded = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION)
            .post(body)
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

                        val status = resultJSONObject.getString("status")
                        ingredienteAdded = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return ingredienteAdded
    }

    fun UpdateIngrediente(id: UUID, ingrediente: Ingrediente) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, ingrediente.toJSON().toString())

        var ingredienteUpdated = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .put(body)
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

                        val status = resultJSONObject.getString("status")
                        ingredienteUpdated = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return ingredienteUpdated
    }

    fun DeleteIngrediente(id: UUID) : Boolean {
        var IngredienteDeleted = false

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + id)
            .delete()
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

                        val status = resultJSONObject.getString("status")
                        IngredienteDeleted = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return IngredienteDeleted
    }
}