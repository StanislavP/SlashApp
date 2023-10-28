package org.bugwriters

import org.bugwriters.paymentprovider.stripe.ClientInfoRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestLocalServerAPI {

    @Headers("Content-Type: application/json")
    @POST("api/payments/charge")
    suspend fun postPaymentSheet(@Body request: ClientInfoRequest): PaymentSheetResponse
}

data class PaymentSheetResponse(
    val paymentIntent: String,
    val customer: String,
    val ephemeralKey: String,
    val publishableKey: String
)