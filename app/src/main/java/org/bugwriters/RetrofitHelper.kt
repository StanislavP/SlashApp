package org.bugwriters

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


enum class Ports(val port: String) {
    TestLocalServer("3000")
}

object RetrofitHelper {
    private const val baseURLTest = "http://95.43.202.26:8090/"

    fun getInstance(): Retrofit {
        val client = OkHttpClient.Builder()
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder().baseUrl(baseURLTest)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
