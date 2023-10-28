package org.bugwriters.connection

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.bugwriters.BackToLoginDialog
import org.bugwriters.GlobalInformationDialog
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.R
import org.bugwriters.connection.models.responses.BasicResponse
import org.bugwriters.printJsonify
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun <T> createRetrofitService(serviceClass: Class<T>): T {
    return Retrofit.Builder().baseUrl("http://192.168.133.171:8080/").client(
        OkHttpClient.Builder().callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
        .create(serviceClass)
}

suspend fun <T : BasicResponse> executeRequest(
    isTest: Boolean = false,
    execute: suspend () -> T
): RequestValidator<T> {
    return try {
        RequestValidator(isServerError = false, value = execute(), isTest)
    } catch (e: Exception) {
        if (e is HttpException && e.message == " HTTP 401") {
            BackToLoginDialog.show()
            RequestValidator(isServerError = true, value = null, isTest)
        }
        Log.d("NetworkError", e.printStackTrace().toString())
        if (!isTest) {
            GlobalInformationDialog.getDialogProperties().setText(e.message ?: e.toString())
            GlobalInformationDialog.show()
        }
        if (GlobalProgressCircle.isOpen()) GlobalProgressCircle.dismiss()
        RequestValidator(isServerError = true, value = null, isTest)
    }
}

data class RequestValidator<T : BasicResponse>(
    val isServerError: Boolean,
    val value: T?,
    val isTest: Boolean,
    var isSuccessful: Boolean = false

) {
    init {
        if (!isServerError) {
            isSuccessful = true
        } else {
            if (GlobalProgressCircle.isOpen()) GlobalProgressCircle.dismiss()
            if (!isTest) {
                GlobalInformationDialog.getDialogProperties().setText(R.string.connection_error)
                GlobalInformationDialog.show()
            }
        }
        value?.printJsonify()
        if (value == null) printJsonify()
    }

    fun onSuccess(execute: (T) -> Unit): RequestValidator<T> {
        if (isSuccessful) execute(value!!)
        return this
    }

    suspend fun onSuccessSuspend(execute: suspend (T) -> Unit): RequestValidator<T> {
        if (isSuccessful) execute(value!!)
        return this
    }

    suspend fun onFailureSuspend(execute: suspend () -> Unit): RequestValidator<T> {
        if (!isSuccessful || isServerError) execute()
        return this
    }

    fun onFailure(execute: () -> Unit): RequestValidator<T> {
        if (!isSuccessful || isServerError) execute()
        return this
    }

    fun onServerError(execute: () -> Unit): RequestValidator<T> {
        if (isServerError) execute()
        return this
    }

    suspend fun onServerErrorSuspend(execute: suspend () -> Unit): RequestValidator<T> {
        if (isServerError) execute()
        return this
    }
}