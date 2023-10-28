package org.bugwriters.views.main_screen.client.checkout

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bugwriters.ShoppingCart
import org.bugwriters.paymentprovider.stripe.StripeHelper
import org.bugwriters.ui.theme.Green
import org.bugwriters.views.TapAppBar


@Composable
fun CheckoutView(viewModel: CheckoutViewModel, navController: NavController) {

    LaunchedEffect(key1 = StripeHelper.paymentState.value) {
        when(StripeHelper.paymentState.value) {
            StripeHelper.PaymentStates.CANCELED -> {}
            StripeHelper.PaymentStates.FAILED -> {}
            StripeHelper.PaymentStates.COMPLETED-> {
                ShoppingCart.clearCart()
                navController.popBackStack()
                StripeHelper.reset()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TapAppBar(
                title = "Checkout",
                onBackClick = { return@TapAppBar true },
                navController = navController,
            )
        }

    ) { innerPadding ->
        if (viewModel.isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(ShoppingCart.totalAmount.value.toPlainString())
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.pay()
                        }
                    }
                ) {
                    Text("PAY")
                }
            }
        }
    }
}
