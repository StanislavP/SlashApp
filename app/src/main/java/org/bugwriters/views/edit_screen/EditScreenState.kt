package org.bugwriters.views.edit_screen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.bugwriters.ErrorMessages

class EditScreenState {
    var name by mutableStateOf("")
    var price by mutableStateOf("")
    var description by mutableStateOf("")
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
    val errorMassageDescription by mutableStateOf(ErrorMessages.emptyField)
}