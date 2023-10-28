package org.bugwriters.connection

import org.bugwriters.Config
import org.bugwriters.connection.models.bodies.LoginBody
import org.bugwriters.connection.models.bodies.RegisterBody
import org.bugwriters.connection.models.responses.BasicResponse
import org.bugwriters.connection.models.responses.LoginResponse
import org.bugwriters.connection.models.responses.PaymentSheetResponse
import org.bugwriters.paymentprovider.stripe.ClientInfoRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface API {
    @Headers("Content-Type: application/json")
    @POST("api/payments/charge")
    suspend fun postPaymentSheet(
        @Body request: ClientInfoRequest,
        @Header("cookie") cookie: String = Config.cookie
    ): PaymentSheetResponse

    @Headers("Content-Type: application/json")
    @POST("api/auth/signup")
    suspend fun postSignup(@Body request: RegisterBody): BasicResponse

    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    suspend fun postLogin(@Body request: LoginBody): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/auth/logout")
    suspend fun postLogOut(): BasicResponse

    @GET("api/test/user")
    suspend fun isCookieValid(@Header("cookie") cookie: String = Config.cookie): BasicResponse


}


