package org.bugwriters

import androidx.compose.runtime.Composable
import org.bugwriters.dialog_controllers.CustomDialog
import org.bugwriters.dialog_controllers.DialogBuilder
import org.bugwriters.dialog_controllers.DialogTypes

object GlobalInformationDialog {
    private val dialog =
        DialogBuilder().setType(DialogTypes.InformationDialog).create()

    init {
        dialog.properties.dismissOnBackClick = false
        dialog.properties.dismissOnClickOutside = false
    }

    @Composable
    fun View() {
        dialog.View()
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun isDialogOpen(): Boolean {
        return dialog.isOpen()
    }

    fun getDialogProperties(): CustomDialog.Properties {
        return dialog.properties
    }


}