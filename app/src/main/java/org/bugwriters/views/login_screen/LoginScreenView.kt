package org.bugwriters.views.login_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.bugwriters.Screens
import org.bugwriters.reusable_ui_elements.BasicButton
import org.bugwriters.reusable_ui_elements.TextButton
import org.bugwriters.reusable_ui_elements.TextField
import org.bugwriters.reusable_ui_elements.ViewHolder
import org.bugwriters.ui.theme.Green


@Composable
fun LoginScreenView(state: LoginViewState, navController: NavController) {
    val dp40 = remember {
        40.dp
    }
    ViewHolder(
        Alignment.CenterHorizontally, Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .size(360.dp, 450.dp)
                .shadow(5.dp, spotColor = Color.Black, ambientColor = Green),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "LOGIN", fontSize = 24.sp, color = Green)
            Spacer(modifier = Modifier.height(dp40))
            UsernameField(state, it)
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(state, it)
            Spacer(modifier = Modifier.height(dp40))
            BasicButton("Log in", Green, enabled = true) {
                navController.navigate(Screens.main_screen_business) {
                    launchSingleTop = true
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "You don't have an account?")

            TextButton(label = "Create Business", color = Green, onclickColor = Color.DarkGray) {
                navController.navigate(Screens.register_business) {
                    launchSingleTop = true
//                    popUpTo(Screens.login) {
//                        inclusive = true
//                    }
                }
            }
            Text(text = "or")
            TextButton(label = "Create Client", color = Green, onclickColor = Color.DarkGray) {
                navController.navigate(Screens.register_client) {
                    launchSingleTop = true
//                    popUpTo(Screens.login) {
//                        inclusive = true
//                    }
                }
            }
            Text(text = "account!")
        }
    }
}

@Composable
private fun UsernameField(state: LoginViewState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Email",
        state.email,
        { value -> state.email = value })
}

@Composable
private fun PasswordField(state: LoginViewState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Password",
        state.password,
        { value -> state.password = value },
        visualTransformation = PasswordVisualTransformation()
    )
}