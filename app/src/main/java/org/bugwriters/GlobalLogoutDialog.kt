package org.bugwriters

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.dialog_controllers.CustomDialog
import org.bugwriters.dialog_controllers.DialogBuilder
import org.bugwriters.dialog_controllers.DialogTypes

object GlobalLogoutDialog {
    private lateinit var dialog: CustomDialog


    fun init(navController: NavController) {
        dialog =
            DialogBuilder().setType(DialogTypes.ConfirmDialog).setOnConfirm {
                CoroutineScope(Dispatchers.IO).launch {
                    val service = createRetrofitService(API::class.java)
                    executeRequest { service.postLogOut() }.onSuccess {
                        Config.clear()
                        MainScope().launch{
                            navController.navigate(Screens.login) {
                                launchSingleTop = true
                                popUpTo(Screens.login)
                            }
                        }
                    }
                }

            }.create()
        dialog.properties.dismissOnBackClick = false
        dialog.properties.dismissOnClickOutside = false
        dialog.properties.setText(R.string.log_out_massege)
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
}