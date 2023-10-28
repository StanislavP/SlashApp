package org.bugwriters

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.CreateIntentResult
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.bugwriters.paymentprovider.stripe.ClientInfo
import org.bugwriters.paymentprovider.stripe.StripeHelper
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bugwriters.ui.theme.SlashAppTheme
import org.bugwriters.views.EditOfferScreenView
import java.math.BigDecimal
import org.bugwriters.views.register.register_client.RegisterClientState
import org.bugwriters.views.register.register_client.RegisterViewClient
import org.bugwriters.views.login_screen.LoginScreenView
import org.bugwriters.views.login_screen.LoginViewState
import org.bugwriters.views.main_screen.business.MainScreenBusinessView
import org.bugwriters.views.main_screen.client.MainScreenClientView
import org.bugwriters.views.main_screen.client.checkout.CheckoutView
import org.bugwriters.views.main_screen.client.checkout.CheckoutViewModel
import org.bugwriters.views.register.register_business.RegisterBusinessState
import org.bugwriters.views.register.register_business.RegisterViewBusiness

class MainActivity : ComponentActivity() {

    val isLoading = mutableStateOf(false)
    val amount = mutableStateOf(BigDecimal("0.01"))
    val activity = this
    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)

        StripeHelper.init(activity)

        setContent {
            val navController = rememberNavController()
            SlashAppTheme {
                NavHost(
                    navController = navController, startDestination = Screens.login
                ) {
                    composable(Screens.login) {
                        val state = remember {
                            LoginViewState()
                        }
                        LoginScreenView(state, navController)
                    }
                    composable(Screens.register_client) {
                        val state = remember {
                            RegisterClientState()
                        }
                        RegisterViewClient(state)
                    }
                    composable(Screens.register_business) {
                        val state = remember {
                            RegisterBusinessState()
                        }
                        RegisterViewBusiness(state)
                    }
                    composable(Screens.main_screen_business) {
                        MainScreenBusinessView(navController)
                    }
                    composable(Screens.edit_offer) {
                        EditOfferScreenView()
                    }
                    composable(Screens.main_screen_client) {

                        MainScreenClientView(navController)
                    }
                    composable(Screens.checkout) {
                        val viewModel: CheckoutViewModel = viewModel {
                            CheckoutViewModel(
                                navController = navController
                            )
                        }

                        CheckoutView(viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}