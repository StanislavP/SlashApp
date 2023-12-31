package org.bugwriters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.bugwriters.views.SplashScreen
import org.bugwriters.views.edit_screen.EditOfferScreenView
import org.bugwriters.views.edit_screen.ViewType
import org.bugwriters.views.login_screen.LoginScreenView
import org.bugwriters.views.login_screen.LoginViewState
import org.bugwriters.views.main_screen.business.MainScreenBusinessView
import org.bugwriters.views.main_screen.client.MainScreenClientView
import org.bugwriters.views.register.register_business.RegisterBusinessState
import org.bugwriters.views.register.register_business.RegisterViewBusiness
import org.bugwriters.views.register.register_client.RegisterClientState
import org.bugwriters.views.register.register_client.RegisterViewClient
import org.bugwriters.views.shopping_cart.ShoppingCartView
import java.math.BigDecimal

class MainActivity : ComponentActivity() {

    val amount = mutableStateOf(BigDecimal("0.01"))
    private val activity = this
    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        SharedPreferences.init(baseContext)
        Config.getPreferences()

        StripeHelper.init(activity)
        setContent {
            val navController = rememberNavController()
            GlobalLogoutDialog.init(navController)
            BackToLoginDialog.init(navController)

            SlashAppTheme {
                NavHost(
                    navController = navController, startDestination = Screens.splashScreen
                ) {
                    composable(Screens.splashScreen, exitTransition = { exitDown(this) }) {
                        SplashScreen(navController)
                    }
                    composable(Screens.login, enterTransition = { enterDown(this) }) {
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
                        EditOfferScreenView(
                            ViewType.EDIT,
                            navController = navController,
                            id?.toLong()
                        )
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
                }
            }
            GlobalProgressCircle.View()
            GlobalInformationDialog.View()
            GlobalLogoutDialog.View()
            BackToLoginDialog.View()

        }
    }
}