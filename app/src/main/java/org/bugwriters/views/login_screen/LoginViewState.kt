package org.bugwriters.views.login_screen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bugwriters.Config
import org.bugwriters.ErrorMessages
import org.bugwriters.GlobalInformationDialog
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.R
import org.bugwriters.Screens
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.models.bodies.LoginBody
import org.bugwriters.connection.models.bodies.Roles
import org.bugwriters.printJsonify

class LoginViewState {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
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
        password.isBlank()
    }
    val isError = derivedStateOf {
        isErrorEmail.value || isPasswordError.value
    }
    var errorMassageEmail by mutableStateOf(ErrorMessages.emptyField)
    val errorMassagePassword by mutableStateOf(ErrorMessages.emptyField)

    suspend fun login(navController: NavController): Roles? {

        val service = createRetrofitService(API::class.java)
        var role: Roles? = null

        try {
            val loginBody = LoginBody(email, password)
            loginBody.printJsonify()
            val response = service.postLogin(loginBody)
            if (response.code() == 200) {
                var cookie = response.raw().headers("Set-Cookie")[0] ?: ""
                cookie = cookie.split(";")[0]
                val name = response.body()?.username ?: ""
                val email = response.body()?.email ?: ""
                val roles = response.body()?.roles ?: ""
                Config.remember(cookie, name, email, roles)
                role = try {
                    Roles.valueOf(response.body()?.roles ?: "")
                } catch (e: Exception) {
                    null
                }
                withContext(Dispatchers.Main) {
                    if (role != null && role == Roles.ROLE_CLIENT) navController.navigate(
                        Screens.main_screen_client
                    ) {
                        launchSingleTop = true
                        popUpTo(Screens.login) {
                            inclusive = true
                        }
                    }
                    else if (role != null && role == Roles.ROLE_BUSINESS) navController.navigate(
                        Screens.main_screen_business
                    ) {
                        launchSingleTop = true
                        popUpTo(Screens.login) {
                            inclusive = true
                        }
                    }
                    else {
                        GlobalInformationDialog.getDialogProperties()
                            .setText("Wrong login credentials")
                        GlobalInformationDialog.show()
                    }
                }
            } else if (response.code() == 401) {
                GlobalInformationDialog.getDialogProperties().setText(R.string.unauthorised)
                GlobalInformationDialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        GlobalProgressCircle.dismiss()
        return role
    }
}