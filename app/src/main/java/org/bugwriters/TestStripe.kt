package org.bugwriters

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestStripe(

) {

    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String

    lateinit var response: PaymentSheetResponse

    suspend fun init(paymentSheet: PaymentSheet) {


        val api = RetrofitHelper.getInstance().create(TestLocalServerAPI::class.java)
        CoroutineScope(Dispatchers.IO).launch {

        }
    }



    fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "SLASH APP",
                customer = customerConfig,
                allowsDelayedPaymentMethods = false
            )
        )
    }

}