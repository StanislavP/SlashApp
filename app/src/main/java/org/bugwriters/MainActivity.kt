package org.bugwriters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bugwriters.ui.theme.SlashAppTheme
import org.bugwriters.views.register.register_client.RegisterClientState
import org.bugwriters.views.register.register_client.RegisterViewClient
import org.bugwriters.views.login_screen.LoginScreenView
import org.bugwriters.views.login_screen.LoginViewState
import org.bugwriters.views.main_screen.business.MainScreenBusinessView
import org.bugwriters.views.register.register_business.RegisterBusinessState
import org.bugwriters.views.register.register_business.RegisterViewBusiness

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SlashAppTheme {
                NavHost(
                    navController = navController, startDestination = Screens.login
                ) {
//                composable(Screens.splashScreen) {
//
//                }
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

                        MainScreenBusinessView()
                    }
                }
            }
        }
    }
}