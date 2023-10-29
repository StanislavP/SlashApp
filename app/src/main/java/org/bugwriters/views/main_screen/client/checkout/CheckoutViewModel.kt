package org.bugwriters.views.main_screen.client.checkout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.bugwriters.ShoppingCart
import org.bugwriters.paymentprovider.stripe.ClientInfo
import org.bugwriters.paymentprovider.stripe.StripeHelper

class CheckoutViewModel: ViewModel() {

    private val isLoading = mutableStateOf(false)

    suspend fun pay() {
        initiatePayment()
        StripeHelper.pay()
    }

    private suspend fun initiatePayment() {
        StripeHelper.initiatePayment(
            ClientInfo(
                "test",
                ShoppingCart.getFullAmount(),
                "USD"
            )
        )

        while (!StripeHelper.canPay) {
            delay(100L)
        }

        withContext(Dispatchers.Main) {
            isLoading.value = false
        }
    }
}