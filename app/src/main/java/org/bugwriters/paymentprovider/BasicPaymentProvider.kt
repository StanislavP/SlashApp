package org.bugwriters.paymentprovider

import androidx.activity.ComponentActivity
import org.bugwriters.paymentprovider.stripe.ClientInfo

interface BasicPaymentProvider {

    enum class PaymentProvider{
        CARD, CRYPTO
    }
    fun init(activity: ComponentActivity)

    fun pay()
}