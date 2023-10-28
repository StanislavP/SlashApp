package org.bugwriters.connection.models.responses

data class PaymentSheetResponse(
    val paymentIntent: String,
    val customer: String,
    val ephemeralKey: String,
    val publishableKey: String
):BasicResponse()