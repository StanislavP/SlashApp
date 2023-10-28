package org.bugwriters.views.login_screen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.bugwriters.ErrorMessages

class LoginViewState {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    val isErrorName = derivedStateOf {
        email.isEmpty()
    }
    val isPasswordError = derivedStateOf {
        password.isEmpty()
    }
    val isError = derivedStateOf {
        isErrorName.value || isPasswordError.value
    }
    val errorMassageName by mutableStateOf(ErrorMessages.emptyField)
    val errorMassagePassword by mutableStateOf(ErrorMessages.emptyField)
}