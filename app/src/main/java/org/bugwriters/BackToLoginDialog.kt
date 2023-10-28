package org.bugwriters

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.dialog_controllers.CustomDialog
import org.bugwriters.dialog_controllers.DialogBuilder
import org.bugwriters.dialog_controllers.DialogTypes

object BackToLoginDialog {
    private lateinit var dialog: CustomDialog


    fun init(navController: NavController) {
        dialog =
            DialogBuilder().setType(DialogTypes.ConfirmDialog).setOnConfirm {
                Config.clear()
                MainScope().launch {
                    navController.navigate(Screens.login) {
                        launchSingleTop = true
                        popUpTo(Screens.login)
                    }
                }
            }.create()
        dialog.properties.dismissOnBackClick = false
        dialog.properties.dismissOnClickOutside = false
        dialog.properties.setText(R.string.sesion_expired)
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