package org.bugwriters.dialog_controllers

import androidx.compose.runtime.Composable
import org.bugwriters.reusable_ui_elements.ConfirmDialog
import org.bugwriters.reusable_ui_elements.InformationDialog

enum class DialogTypes {
    ConfirmDialog, InformationDialog
}

 class DialogBuilder {


    private var clickOutside: () -> Unit = {}
    private var confirm: () -> Unit = {}
    private var decline: () -> Unit = {}
    private var type = DialogTypes.ConfirmDialog
    private val controller = object : DialogController() {
        override fun onClickOutside() {
            clickOutside()
        }

        override fun onConfirm() {
            confirm()
        }

        override fun onDecline() {
            decline()
        }

    }

    fun setType(dialogTypes: DialogTypes): DialogBuilder {
        type = dialogTypes
        return this
    }

    fun setOnClickOutside(onClickOutside: () -> Unit): DialogBuilder {
        clickOutside = onClickOutside
        return this
    }

    fun setOnConfirm(onConfirm: () -> Unit): DialogBuilder {
        confirm = onConfirm
        return this
    }

    fun setOnDecline(onDecline: () -> Unit): DialogBuilder {
        decline = onDecline
        return this
    }

    fun create(): CustomDialog {

        val dialog = CustomDialog(controller)


        if (type == DialogTypes.ConfirmDialog) {
            dialog.setView @Composable {
                ConfirmDialog(customDialog = dialog)
            }
        } else if (type == DialogTypes.InformationDialog) {
            dialog.setView @Composable {
                InformationDialog(customDialog = dialog)
            }
        }

        return dialog


    }
}
