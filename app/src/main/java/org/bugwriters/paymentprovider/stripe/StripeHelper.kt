package org.bugwriters.paymentprovider.stripe

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bugwriters.RetrofitHelper
import org.bugwriters.TestLocalServerAPI
import org.bugwriters.paymentprovider.BasicPaymentProvider
import org.bugwriters.views.main_screen.client.checkout.CheckoutViewModel
import java.math.BigDecimal
import java.math.RoundingMode

object StripeHelper : BasicPaymentProvider {

    lateinit var activity: ComponentActivity
    lateinit var type: BasicPaymentProvider.PaymentProvider
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String
    lateinit var toast: Toast
    const val TAG: String = "STRIPE"

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
        val api = RetrofitHelper.getInstance().create(TestLocalServerAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            paymentState.value = PaymentStates.STARTED
            val response = api.postPaymentSheet(client.toRequest())
            paymentIntentClientSecret = response.paymentIntent
            customerConfig = PaymentSheet.CustomerConfiguration(
                response.customer,
                response.ephemeralKey
            )
            val publishableKey = response.publishableKey
            PaymentConfiguration.init(activity.applicationContext, publishableKey)

            canPay = true
        }
    }

    override fun pay() {
        presentPaymentSheet()
        canPay = false
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d(TAG, "Payment Canceled")
                Toast.makeText(activity.applicationContext, "Payment Canceled", Toast.LENGTH_SHORT).show()
//                toast.setText("Payment Canceled")
//                toast.show()
                paymentState.value = PaymentStates.CANCELED
            }

            is PaymentSheetResult.Failed -> {
                Log.d(TAG, "Payment Error: ${paymentSheetResult.error}")
                Toast.makeText(activity.applicationContext, "Payment Error: ${paymentSheetResult.error}", Toast.LENGTH_SHORT).show()
//                toast.setText("Payment Error: ${paymentSheetResult.error}")
//                toast.show()
                paymentState.value = PaymentStates.FAILED
            }

            is PaymentSheetResult.Completed -> {
                Log.d(TAG, "Payment Completed ")
                Toast.makeText(activity.applicationContext, "Payment Successful", Toast.LENGTH_SHORT).show()
//                toast.setText("Payment Successful")
//                toast.show()
                paymentState.value = PaymentStates.COMPLETED
            }
        }
    }

    fun reset() {
        paymentState.value = PaymentStates.NOT_INIT
    }

    fun presentPaymentSheet() {
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