package org.bugwriters.views.edit_screen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bugwriters.ErrorMessages
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.Screens
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.connection.models.bodies.AddOfferBody
import org.bugwriters.connection.models.bodies.ProductType
import java.math.RoundingMode

class EditScreenState(val navController: NavController,val id: Long?) {
    var isLoading by mutableStateOf(false)
    var name by mutableStateOf("")
    var price by mutableStateOf("")
    var description by mutableStateOf("")
    var type by mutableStateOf(ProductType.SERVICE)
    val isErrorName = derivedStateOf {
        name.isEmpty()
    }
    val isErrorPrice = derivedStateOf {
        price.isEmpty()
    }
    val isDescriptionError = derivedStateOf {
        description.isEmpty()
    }
    val isError = derivedStateOf {
        isErrorName.value || isErrorPrice.value || isDescriptionError.value
    }
    val errorMassageName by mutableStateOf(ErrorMessages.emptyField)
    val errorMassagePrice by mutableStateOf(ErrorMessages.emptyField)

    init {
        if (id != null) {
            isLoading = true
            CoroutineScope(Dispatchers.IO).launch {
                val service = createRetrofitService(API::class.java)
                executeRequest { service.getProduct(long = id) }.onSuccessSuspend {
                    withContext(Dispatchers.Main) {
                        name = it.name
                        price = it.price.toString()
                        description = it.description
                        type = it.type
                    }
                    isLoading = false
                }.onFailure { isLoading = false }.onServerError { isLoading = false }
            }
        }
    }


    fun addItem() {
        GlobalProgressCircle.show()
        val service = createRetrofitService(API::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            executeRequest {
                service.addOffer(
                    AddOfferBody(
                        name,
                        description,
                        price.toBigDecimal().setScale(2, RoundingMode.HALF_UP),
                        type
                    )
                )
            }.onSuccessSuspend {
                GlobalProgressCircle.dismiss()
                withContext(Dispatchers.Main) {
                    navController.navigate(Screens.main_screen_business) {
                        launchSingleTop = true
                        popUpTo(Screens.main_screen_business)
                    }
                }
            }.onFailure {
                GlobalProgressCircle.dismiss()
            }.onServerError {
                GlobalProgressCircle.dismiss()
            }
        }
    }
    fun editItem() {
        GlobalProgressCircle.show()
        val service = createRetrofitService(API::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            executeRequest {
                service.editOffer(
                    AddOfferBody(
                        name,
                        description,
                        price.toBigDecimal().setScale(2, RoundingMode.HALF_UP),
                        type,
                        id = id!!
                    )
                )
            }.onSuccessSuspend {
                GlobalProgressCircle.dismiss()
                withContext(Dispatchers.Main) {
                    navController.navigate(Screens.main_screen_business) {
                        launchSingleTop = true
                        popUpTo(Screens.main_screen_business)
                    }
                }
            }.onFailure {
                GlobalProgressCircle.dismiss()
            }.onServerError {
                GlobalProgressCircle.dismiss()
            }
        }
    }
}