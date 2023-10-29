package org.bugwriters.connection

import org.bugwriters.Config
import org.bugwriters.connection.models.bodies.AddOfferBody
import org.bugwriters.connection.models.bodies.LoginBody
import org.bugwriters.connection.models.bodies.MyOffers
import org.bugwriters.connection.models.bodies.RegisterBody
import org.bugwriters.connection.models.responses.BasicResponse
import org.bugwriters.connection.models.responses.BusinessesResponse
import org.bugwriters.connection.models.responses.LoginResponse
import org.bugwriters.connection.models.responses.PaymentSheetResponse
import org.bugwriters.connection.models.responses.Product
import org.bugwriters.connection.models.responses.ProductsResponse
import org.bugwriters.paymentprovider.stripe.ClientInfoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

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

    @Headers("Content-Type: application/json")
    @POST("api/product/add")
    suspend fun addOffer(
        @Body body: AddOfferBody,
        @Header("cookie") cookie: String = Config.cookie
    ): BasicResponse

    @Headers("Content-Type: application/json")
    @POST("api/product/edit")
    suspend fun editOffer(
        @Body body: AddOfferBody,
        @Header("cookie") cookie: String = Config.cookie
    ): BasicResponse

    @Headers("Content-Type: application/json")
    @POST("api/product/delete/{id}")
    suspend fun deleteOffer(
        @Path("id") id: Long,
        @Header("cookie") cookie: String = Config.cookie
    ): BasicResponse

    @GET("api/test/user")
    suspend fun isCookieValid(@Header("cookie") cookie: String = Config.cookie): BasicResponse

    @GET("api/product")
    suspend fun getProducts(@Header("cookie") cookie: String = Config.cookie): ProductsResponse

    @GET("api/business/all")
    suspend fun getAllBusinesses(@Header("cookie") cookie: String = Config.cookie): BusinessesResponse


    @GET("api/product/{id}")
    suspend fun getProduct(
        @Header("cookie") cookie: String = Config.cookie,
        @Path("id") long: Long
    ): Product

    @POST("api/product/mine")
    suspend fun getProductsMine(
        @Body myOffers: MyOffers = MyOffers(),
        @Header("cookie") cookie: String = Config.cookie
    ): ProductsResponse

    @POST("api/business/{id}")
    suspend fun getAllOffersByBusiness(
        @Path("id") id: Long,
        @Header("cookie") cookie: String = Config.cookie
    ): ProductsResponse


}



