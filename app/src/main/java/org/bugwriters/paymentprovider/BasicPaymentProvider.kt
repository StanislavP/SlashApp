package org.bugwriters.paymentprovider

import androidx.activity.ComponentActivity

interface BasicPaymentProvider {

    enum class PaymentProvider{
        CARD, CRYPTO
    }
    fun init(activity: ComponentActivity)

    fun pay()
}