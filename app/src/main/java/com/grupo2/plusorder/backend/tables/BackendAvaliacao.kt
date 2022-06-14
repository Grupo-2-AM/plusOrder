package com.grupo2.plusorder.backend.tables

import com.grupo2.plusorder.backend.Backend.BASE_API
import com.grupo2.plusorder.backend.models.Avaliacao
import com.grupo2.plusorder.backend.models.Prato
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

object BackendAvaliacao {
    private const val BASE_EXTENSION = "Avaliacao/"
    private const val AVALICOES_BY_PRATO = "getAvaliacoesByPrato/"
    private const val MEDIA_BY_PRATO = "getMediaAvaliacaoByPrato/"

    fun GetAllAvaliacoes() : List<Avaliacao> {
        var avaliacoes = arrayListOf<Avaliacao>()

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
                        var avaliacaoJSON = resultArray[index] as JSONObject
                        var avaliacao = Avaliacao.fromJSON(avaliacaoJSON)
                        avaliacoes.add(avaliacao)
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return avaliacoes
    }

    fun GetAllAvaliacoesByIdPrato(idPrato: UUID) : List<Avaliacao> {
        var avaliacoes = arrayListOf<Avaliacao>()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + AVALICOES_BY_PRATO + idPrato)
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
                            var avaliacaoJSON = resultArray[index] as JSONObject
                            var avaliacao = Avaliacao.fromJSON(avaliacaoJSON)
                            avaliacoes.add(avaliacao)
                        }

                        countDownLatch.countDown()
                    }
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return avaliacoes
    }

    fun GetAvaliacao(id: UUID) : Avaliacao? {
        var avaliacao: Avaliacao? = null

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
                        avaliacao = Avaliacao.fromJSON(resultJSONObject)
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return avaliacao
    }

    fun GetMediaAvaliacaoByPrato(idPrato: UUID) : Double? {
        var mediaAval: Double? = null

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_API + BASE_EXTENSION + MEDIA_BY_PRATO + idPrato)
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
                        mediaAval = result.toDoubleOrNull()
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return mediaAval
    }

    // Adds object to database and returns true if successful
    fun AddAvaliacao(avaliacao: Avaliacao) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, avaliacao.toJSON().toString())

        var avaliacaoAdded = false

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
                        avaliacaoAdded = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return avaliacaoAdded
    }

    fun UpdateAvaliacao(id: UUID, avaliacao: Avaliacao) : Boolean {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = RequestBody.create(
            mediaType, avaliacao.toJSON().toString())

        var avaliacaoUpdated = false

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
                        avaliacaoUpdated = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return avaliacaoUpdated
    }

    fun DeleteAvaliacao(id: UUID) : Boolean {
        var avaliacaoDeleted = false

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
                        avaliacaoDeleted = status == "ok"
                    }

                    countDownLatch.countDown()
                }
            }
        })

        // await until request finished
        countDownLatch.await()

        return avaliacaoDeleted
    }
}