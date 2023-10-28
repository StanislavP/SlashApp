package org.bugwriters.views.register.register_business

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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.bugwriters.reusable_ui_elements.BasicButton
import org.bugwriters.reusable_ui_elements.TextField
import org.bugwriters.reusable_ui_elements.ViewHolder
import org.bugwriters.ui.theme.Green
import org.bugwriters.views.register.register_client.RegisterClientState

@Composable
fun RegisterViewBusiness(state: RegisterBusinessState) {
    val dp10 = remember {
        10.dp
    }

    val dp40 = remember {
        40.dp
    }
    ViewHolder(Alignment.CenterHorizontally, Arrangement.Center) {
        Column(
            modifier = Modifier
                .size(360.dp, 450.dp)
                .shadow(5.dp, spotColor = Color.Black, ambientColor = Green)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Register", fontSize = 24.sp, color = Green)
            Spacer(modifier = Modifier.height(dp40))
            NameField(state, it)
            Spacer(modifier = Modifier.height(dp10))
            EmailField(state, it)
            Spacer(modifier = Modifier.height(dp10))
            PasswordField(state, it)
            Spacer(modifier = Modifier.height(dp40))
            ConfirmPasswordField(state,it)
            Spacer(modifier = Modifier.height(dp40))
            BasicButton("Register", Green, enabled = !state.isError.value) {
            }
        }
    }
}

@Composable
private fun NameField(state: RegisterBusinessState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Business name",
        state.name,
        { value -> state.name = value },
        isError = state.isErrorName.value,
        errorText = state.errorMassageName
    )
}

@Composable
private fun EmailField(state: RegisterBusinessState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Email",
        state.email,
        { value -> state.email = value },
        isError = state.isErrorEmail.value,
        errorText = state.errorMassageEmail
    )
}

@Composable
fun PasswordField(state: RegisterBusinessState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Password",
        state.password,
        { value -> state.password = value },
        visualTransformation = PasswordVisualTransformation(),
        isError = state.isPasswordError.value,
        errorText = state.errorMassagePassword
    )
}
@Composable
private fun ConfirmPasswordField(state: RegisterBusinessState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Password",
        state.confirmPassword,
        { value -> state.confirmPassword = value },
        visualTransformation = PasswordVisualTransformation(),
        isError = state.isConfirmPasswordError.value,
        errorText = state.errorMassageConfirmPassword
    )
}