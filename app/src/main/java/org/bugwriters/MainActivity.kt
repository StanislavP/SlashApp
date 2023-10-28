package org.bugwriters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.connection.models.bodies.Roles
import org.bugwriters.paymentprovider.stripe.StripeHelper
import org.bugwriters.ui.theme.SlashAppTheme
import org.bugwriters.views.edit_screen.EditOfferScreenView
import org.bugwriters.views.edit_screen.ViewType
import org.bugwriters.views.login_screen.LoginScreenView
import org.bugwriters.views.login_screen.LoginViewState
import org.bugwriters.views.main_screen.business.MainScreenBusinessView
import org.bugwriters.views.main_screen.client.MainScreenClientView
import org.bugwriters.views.main_screen.client.checkout.CheckoutView
import org.bugwriters.views.main_screen.client.checkout.CheckoutViewModel
import org.bugwriters.views.register.register_business.RegisterBusinessState
import org.bugwriters.views.register.register_business.RegisterViewBusiness
import org.bugwriters.views.register.register_client.RegisterClientState
import org.bugwriters.views.register.register_client.RegisterViewClient
import org.bugwriters.views.shopping_cart.ShoppingCartView
import java.math.BigDecimal

class MainActivity : ComponentActivity() {

    val isLoading = mutableStateOf(false)
    val amount = mutableStateOf(BigDecimal("0.01"))
    val activity = this
    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        val service = createRetrofitService(API::class.java)
        var screen by mutableStateOf(Screens.login)
        SharedPreferences.init(baseContext)
        Config.getPreferences()
        if (Config.cookie.isNotBlank()) {
            withContext(Dispatchers.IO) {
                async {
                    executeRequest(true) {
                        service.isCookieValid()
                    }.onSuccess {
                        screen =
                            if (Config.role != null && Config.role == Roles.ROLE_CLIENT.name) Screens.main_screen_client
                            else if (Config.role != null && Config.role == Roles.ROLE_BUSINESS.name) Screens.main_screen_business
                            else Screens.login
                    }.onFailure {
                        Config.clear()
                    }.onServerError {
                        Config.clear()
                    }
                }.await()
            }
        }
        StripeHelper.init(activity)
        setContent {
            val navController = rememberNavController()
            GlobalLogoutDialog.init(navController)
            BackToLoginDialog.init(navController)

            SlashAppTheme {
                NavHost(
                    navController = navController, startDestination = screen
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
                        RegisterViewClient(state, navController)
                    }
                    composable(Screens.register_business) {
                        val state = remember {
                            RegisterBusinessState()
                        }
                        RegisterViewBusiness(state, navController)
                    }
                    composable(Screens.main_screen_business) {
                        MainScreenBusinessView(navController)
                    }
                    composable(
                        Screens.edit_offer + "?id={id}",
                        arguments = listOf(navArgument("id") {
                            nullable = true
                        })
                    ) {
                        val id = it.arguments?.getString("id", "0")
                        EditOfferScreenView(ViewType.EDIT, navController = navController,id?.toLong())
                    }
                    composable(Screens.add_offer) {
                        EditOfferScreenView(ViewType.ADD, navController, null)
                    }
                    composable(Screens.main_screen_client) {

                        MainScreenClientView(navController)
                    }
                    composable(Screens.shopping_cart) {
                        ShoppingCartView(navController)
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
            GlobalProgressCircle.View()
            GlobalInformationDialog.View()
            GlobalLogoutDialog.View()
            BackToLoginDialog.View()

        }
    }
}