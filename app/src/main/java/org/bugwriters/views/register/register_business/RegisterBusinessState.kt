package org.bugwriters.views.register.register_business

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

class RegisterBusinessState {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    val isErrorName = derivedStateOf {
        name.isEmpty()
    }

    val isErrorEmail = derivedStateOf {
        email.isEmpty()
    }
    val isPasswordError = derivedStateOf {
        password.isEmpty()
    }
    val isConfirmPasswordError = derivedStateOf {
        confirmPassword.isEmpty() || confirmPassword!= password
    }
    val isError = derivedStateOf {
        isErrorName.value ||  isErrorEmail.value || isPasswordError.value || isConfirmPasswordError.value
    }
    val errorMassageName by mutableStateOf(ErrorMessages.emptyField)
    val errorMassageEmail by mutableStateOf(ErrorMessages.emptyField)
    val errorMassagePassword by mutableStateOf(ErrorMessages.emptyField)
    val errorMassageConfirmPassword by mutableStateOf(ErrorMessages.emptyField)

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun register(): Boolean = scope.async {

        val service = createRetrofitService(API::class.java)
        var flag = false
        executeRequest {
            service.postSignup(
                RegisterBody(
                    name,
                    email,
                    password,
                    listOf(Roles.ROLE_BUSINESS)
                )
            )
        }.onSuccess { flag = true }
        return@async flag
    }.await()


}
