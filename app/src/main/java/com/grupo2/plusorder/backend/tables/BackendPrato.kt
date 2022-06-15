package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Conta
import com.grupo2.plusorder.backend.models.Prato
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

object BackendPrato {
    private const val BASE_EXTENSION = "Prato/"
    private const val CATEGORIA_SEARCH_EXTENSION = "pratoByCategoria/"
    private const val NAME_CATEGORIA_SEARCH_EXTENSION = "searchPratoWithCategoria/"
    private const val PRATO_BY_PEDIDO = "pratoByPedido/"
    private const val PRATOS_PAID_TODAY = "getPratosPayedToday/"

    fun GetAllPratos() : List<Prato> {
        var pratos = arrayListOf<Prato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + PRATOS_PAID_TODAY)
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

                    var result = response.body!!.string()
                    var resultArray = JSONArray(result)

                    for (index in 0 until resultArray.length()) {
                        var pratoJSON = resultArray[index] as JSONObject
                        var prato = Prato.fromJSON(pratoJSON)
                        pratos.add(prato)
                    }
                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratos
    }

    fun GetAllPratosPayedToday() : List<Prato> {
        var pratos = arrayListOf<Prato>()

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

                    var result = response.body!!.string()
                    var resultArray = JSONArray(result)

                    for (index in 0 until resultArray.length()) {
                        var pratoJSON = resultArray[index] as JSONObject
                        var prato = Prato.fromJSON(pratoJSON)
                        pratos.add(prato)
                    }
                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratos
    }

    // Get active pratos from a list of pratos
    fun GetPratosAtivos(pratosSearch: List<Prato>) : List<Prato> {
        var activePratos = arrayListOf<Prato>()

        for (prato in pratosSearch)
            if (prato.ativo!!.toInt() == 1) // toInt necessary because prato.ativo is Integer
                activePratos.add(prato)

        return activePratos
    }

    fun GetAllPratosByPedido(idPedido: UUID) : List<Prato>{
        var pratos = arrayListOf<Prato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + PRATO_BY_PEDIDO + idPedido)
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

                    if (response.body != null) {
                        var result = response.body!!.string()
                        var resultArray = JSONArray(result)

                        for (index in 0 until resultArray.length()) {
                            var pratoJSON = resultArray[index] as JSONObject
                            var prato = Prato.fromJSON(pratoJSON)

                            pratos.add(prato)
                        }
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratos
    }

    fun GetAllPratosByCategoria(idCategoriaSearch: UUID) : List<Prato>{
            var pratos = arrayListOf<Prato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + CATEGORIA_SEARCH_EXTENSION + idCategoriaSearch)
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

                    if (response.body != null) {
                        var result = response.body!!.string()
                        var resultArray = JSONArray(result)

                        for (index in 0 until resultArray.length()) {
                            var pratoJSON = resultArray[index] as JSONObject
                            var prato = Prato.fromJSON(pratoJSON)

                            pratos.add(prato)
                        }
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratos
    }

    fun SearchPratosByNameCategoria(nameSearch: String, idCategoriaSearch: UUID) : List<Prato>{
        var pratos = arrayListOf<Prato>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + NAME_CATEGORIA_SEARCH_EXTENSION + nameSearch + "/" + idCategoriaSearch)
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

                    if (response.body != null) {
                        var result = response.body!!.string()
                        var resultArray = JSONArray(result)

                        for (index in 0 until resultArray.length()) {
                            var pratoJSON = resultArray[index] as JSONObject
                            var prato = Prato.fromJSON(pratoJSON)

                            pratos.add(prato)
                        }
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratos
    }

    fun GetPrato(id: UUID) : Prato? {
        var prato: Prato? = null

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

                    if (response.body != null) {
                        var result = response.body!!.string()
                        var resultJSONObject = JSONObject(result)
                        prato = Prato.fromJSON(resultJSONObject)
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return prato
    }

    // Adds object to database and returns true if successful
    fun AddPrato(prato: Prato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, prato.toJSON().toString())

        var pratoAdded = false

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

                    if (response.body != null) {
                        var result = response.body!!.string()
                        var resultJSONObject = JSONObject(result)

                        val status = resultJSONObject.getString("status")
                        pratoAdded = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratoAdded
    }

    fun UpdatePrato(id: UUID, prato: Prato) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, prato.toJSON().toString())

        var pratoUpdated = false

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

                    if (response.body != null) {
                        var result = response.body!!.string()
                        var resultJSONObject = JSONObject(result)

                        val status = resultJSONObject.getString("status")
                        pratoUpdated = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratoUpdated
    }

    fun DeletePrato(id: UUID) : Boolean {
        var pratoDeleted = false

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

                    if (response.body != null) {
                        var result = response.body!!.string()
                        var resultJSONObject = JSONObject(result)

                        val status = resultJSONObject.getString("status")
                        pratoDeleted = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return pratoDeleted
    }
}