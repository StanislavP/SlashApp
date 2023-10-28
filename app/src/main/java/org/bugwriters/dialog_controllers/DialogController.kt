package org.bugwriters.dialog_controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

abstract class DialogController {
    var isOpened by mutableStateOf(false)

    fun openDialog() {
        isOpened = true
    }

    fun closeDialog() {
        isOpened = false
    }

    abstract fun onClickOutside()
    abstract fun onConfirm()
    abstract fun onDecline()

}