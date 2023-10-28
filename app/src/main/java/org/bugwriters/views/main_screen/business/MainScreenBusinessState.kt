package org.bugwriters.views.main_screen.business

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.Screens
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.connection.models.responses.Product
import org.bugwriters.dialog_controllers.DialogBuilder
import org.bugwriters.dialog_controllers.DialogTypes

class MainScreenBusinessState(val navController: NavController) {
    var items = mutableStateListOf<Product>()
    var deleteId = 0L

    val dialog = DialogBuilder().setType(DialogTypes.ConfirmDialog).setOnConfirm {
        delete()
    }.create()

    init {
        dialog.properties.setText("Delete item?")
        updateItems()
    }

    fun updateItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val service = createRetrofitService(API::class.java)
            executeRequest { service.getProductsMine() }.onSuccessSuspend {
                withContext(Dispatchers.Main) {
                    items.clear()
                    items.addAll(it.productsResponse)
                }
            }
        }
    }

    private fun delete() {
        if (deleteId != 0L) {
            GlobalProgressCircle.show()
            val service = createRetrofitService(API::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                executeRequest { service.deleteOffer(deleteId) }.onSuccessSuspend {
                    withContext(Dispatchers.Main) {
                        updateItems()
                        deleteId = 0L
                        GlobalProgressCircle.dismiss()
                    }
                }.onFailure {
                    deleteId = 0L
                    GlobalProgressCircle.dismiss()
                }.onServerError {
                    deleteId = 0L
                    GlobalProgressCircle.dismiss()
                }
            }
        }
    }
}
