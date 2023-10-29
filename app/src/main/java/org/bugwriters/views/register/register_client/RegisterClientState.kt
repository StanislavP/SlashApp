package org.bugwriters.views.register.register_client

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.bugwriters.ErrorMessages
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.connection.models.bodies.RegisterBody
import org.bugwriters.connection.models.bodies.Roles

class RegisterClientState {
    var name by mutableStateOf("")
    var familyName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    val isErrorName = derivedStateOf {
        name.isBlank()
    }
    val isErrorFamilyName = derivedStateOf {
        familyName.isBlank()
    }
    val isErrorEmail = derivedStateOf {
        if (email.isBlank()) {
            errorMassageEmail = ErrorMessages.emptyField
            return@derivedStateOf email.isBlank()
        } else if (!email.matches(Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))) {
            errorMassageEmail = ErrorMessages.fieldShouldBeEmail
            return@derivedStateOf true
        } else return@derivedStateOf false
    }
    val isPasswordError = derivedStateOf {
        if (password.isBlank()) {
            errorMassagePassword = ErrorMessages.emptyField
            return@derivedStateOf password.isBlank()
        } else if (password.trim().length < 6) {
            errorMassagePassword = ErrorMessages.password6SymbolsError
            return@derivedStateOf true
        } else return@derivedStateOf false
    }
    val isConfirmPasswordError = derivedStateOf {
        if (confirmPassword.isBlank()) {
            errorMassageConfirmPassword = ErrorMessages.emptyField
            return@derivedStateOf confirmPassword.isBlank()
        } else if (confirmPassword != password) {
            errorMassageConfirmPassword = ErrorMessages.passwordsNotMatching
            return@derivedStateOf confirmPassword != password
        } else return@derivedStateOf false

    }
    val isError = derivedStateOf {
        isErrorName.value || isErrorFamilyName.value || isErrorEmail.value || isPasswordError.value || isConfirmPasswordError.value
    }
    val errorMassageName by mutableStateOf(ErrorMessages.emptyField)
    val errorMassageFamilyName by mutableStateOf(ErrorMessages.emptyField)
    var errorMassageEmail by mutableStateOf(ErrorMessages.emptyField)
    var errorMassagePassword by mutableStateOf(ErrorMessages.emptyField)
    var errorMassageConfirmPassword by mutableStateOf(ErrorMessages.emptyField)

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun register(): Boolean = scope.async {

        val service = createRetrofitService(API::class.java)
        var flag = false
        val fullName = "$name $familyName"
        executeRequest {
            service.postSignup(
                RegisterBody(
                    fullName,
                    email,
                    password,
                    listOf(Roles.ROLE_CLIENT)
                )
            )
        }.onSuccess { flag = true }
        return@async flag
    }.await()

}
