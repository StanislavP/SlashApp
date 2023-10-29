package org.bugwriters.paymentprovider.stripe

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.paymentprovider.BasicPaymentProvider
import java.math.BigDecimal
import java.math.RoundingMode

object StripeHelper : BasicPaymentProvider {

    private lateinit var activity: ComponentActivity
    private lateinit var type: BasicPaymentProvider.PaymentProvider
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    private lateinit var paymentIntentClientSecret: String
    private lateinit var toast: Toast
    private const val TAG: String = "STRIPE"

    var paymentState = mutableStateOf(PaymentStates.NOT_INIT)

    var canPay = false

    enum class PaymentStates {
        NOT_INIT, STARTED, PROCESSING, FAILED, COMPLETED, CANCELED
    }


    override fun init(activity: ComponentActivity) {
        type = BasicPaymentProvider.PaymentProvider.CARD
        this.activity = activity
        toast = Toast(this.activity.applicationContext)
        paymentSheet = PaymentSheet(activity, ::onPaymentSheetResult)
    }

    fun initiatePayment(client: ClientInfo) {
        val api = createRetrofitService(API::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            paymentState.value = PaymentStates.STARTED
            GlobalProgressCircle.show()
            executeRequest { api.postPaymentSheet(client.toRequest()) }.onSuccess {
                paymentIntentClientSecret = it.paymentIntent
                customerConfig = PaymentSheet.CustomerConfiguration(
                    it.customer,
                    it.ephemeralKey
                )
                val publishableKey = it.publishableKey
                PaymentConfiguration.init(activity.applicationContext, publishableKey)

                canPay = true
            }
        }
    }

    override fun pay() {
        presentPaymentSheet()
        canPay = false
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d(TAG, "Payment Canceled")
                Toast.makeText(activity.applicationContext, "Payment Canceled", Toast.LENGTH_SHORT)
                    .show()
                paymentState.value = PaymentStates.CANCELED
            }

            is PaymentSheetResult.Failed -> {
                Log.d(TAG, "Payment Error: ${paymentSheetResult.error}")
                Toast.makeText(
                    activity.applicationContext,
                    "Payment Error: ${paymentSheetResult.error}",
                    Toast.LENGTH_SHORT
                ).show()
                paymentState.value = PaymentStates.FAILED
            }

            is PaymentSheetResult.Completed -> {
                Log.d(TAG, "Payment Completed ")
                Toast.makeText(
                    activity.applicationContext,
                    "Payment Successful",
                    Toast.LENGTH_SHORT
                ).show()
                paymentState.value = PaymentStates.COMPLETED
            }
        }
    }

    fun reset() {
        paymentState.value = PaymentStates.NOT_INIT
    }

    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Slash App",
                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business handles
                // delayed notification payment methods like US bank accounts.
                allowsDelayedPaymentMethods = true
            )
        )
    }
}


data class ClientInfo(
    val email: String,
    val amount: Long,
    val currency: String,
) {
    fun toRequest(): ClientInfoRequest {
        return ClientInfoRequest(
            amount,
            currency
        )
    }
}

data class ClientInfoRequest(
    val amount: Long,
    val currency: String
)

fun BigDecimal.toCurrencyLong(): Long {
    return this.setScale(2, RoundingMode.HALF_UP)
        .multiply(100.toBigDecimal()).toLong()
}