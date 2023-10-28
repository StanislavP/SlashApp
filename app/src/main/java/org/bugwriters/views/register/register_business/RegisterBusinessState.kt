package org.bugwriters.views.register.register_business

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.bugwriters.ErrorMessages

class RegisterBusinessState {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    val isErrorName = derivedStateOf {
        name.isEmpty()
    }

    val isErrorEmail = derivedStateOf {
        email.isEmpty()
    }
    val isPasswordError = derivedStateOf {
        password.isEmpty()
    }
    val isError = derivedStateOf {
        isErrorName.value ||  isErrorEmail.value || isPasswordError.value
    }
    val errorMassageName by mutableStateOf(ErrorMessages.emptyField)
    val errorMassageEmail by mutableStateOf(ErrorMessages.emptyField)
    val errorMassagePassword by mutableStateOf(ErrorMessages.emptyField)

}
