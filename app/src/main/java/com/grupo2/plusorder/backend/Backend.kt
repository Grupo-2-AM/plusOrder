package com.grupo2.plusorder.backend

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Backend {

    const val BASE_API = ""

    fun <T: Any> getAllObject(callback: ((List<T>)->Unit), stringObject : String)
    {
        var data = arrayListOf<T>()
        GlobalScope.launch(Dispatchers.IO){
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + stringObject)
        }
    }
}