package org.bugwriters.views.register.register_client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.Screens
import org.bugwriters.reusable_ui_elements.BasicButton
import org.bugwriters.reusable_ui_elements.Card
import org.bugwriters.reusable_ui_elements.TextField
import org.bugwriters.reusable_ui_elements.ViewHolder
import org.bugwriters.ui.theme.Green

@Composable
fun RegisterViewClient(state: RegisterClientState,navController: NavController) {
    val dp10 = remember {
        10.dp
    }

    val dp40 = remember {
        40.dp
    }
    ViewHolder(Alignment.CenterHorizontally, Arrangement.Center) {
        Card{
            Text(text = "Register", fontSize = 24.sp, color = Green)
            Spacer(modifier = Modifier.height(dp40))
            NameField(state, it)
            Spacer(modifier = Modifier.height(dp10))
            FamilyNameField(state, it)
            Spacer(modifier = Modifier.height(dp10))
            EmailField(state, it)
            Spacer(modifier = Modifier.height(dp10))
            PasswordField(state, it)
            Spacer(modifier = Modifier.height(dp10))
            ConfirmPasswordField(state,it)
            Spacer(modifier = Modifier.height(dp40))
            BasicButton("Register", Green, enabled = !state.isError.value) {
                GlobalProgressCircle.show()
                CoroutineScope(Dispatchers.IO).launch {
                    val flag = state.register()
                    withContext(Dispatchers.Main) {

                        if (flag) navController.navigate(Screens.login) {
                            launchSingleTop = true
                            popUpTo(Screens.login)
                        }
                        GlobalProgressCircle.dismiss()
                    }
                }
            }
        }
    }
}

@Composable
private fun NameField(state: RegisterClientState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Name",
        state.name,
        { value -> state.name = value },
        isError = state.isErrorName.value,
        errorText = state.errorMassageName,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text,imeAction = ImeAction.Next)
    )
}

@Composable
private fun FamilyNameField(state: RegisterClientState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Family name",
        state.familyName,
        { value -> state.familyName = value },
        isError = state.isErrorFamilyName.value,
        errorText = state.errorMassageFamilyName,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text,imeAction = ImeAction.Next)
    )
}

@Composable
private fun EmailField(state: RegisterClientState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Email",
        state.email,
        { value -> state.email = value },
        isError = state.isErrorEmail.value,
        errorText = state.errorMassageEmail,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email,imeAction = ImeAction.Next)
    )
}

@Composable
private fun PasswordField(state: RegisterClientState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Password",
        state.password,
        { value -> state.password = value },
        visualTransformation = PasswordVisualTransformation(),
        isError = state.isPasswordError.value,
        errorText = state.errorMassagePassword,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next)
    )
}
@Composable
private fun ConfirmPasswordField(state: RegisterClientState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Confirm password",
        state.confirmPassword,
        { value -> state.confirmPassword = value },
        visualTransformation = PasswordVisualTransformation(),
        isError = state.isConfirmPasswordError.value,
        errorText = state.errorMassageConfirmPassword,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password,imeAction = ImeAction.Done)
    )
}