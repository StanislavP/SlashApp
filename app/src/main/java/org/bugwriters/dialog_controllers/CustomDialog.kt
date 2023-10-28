package org.bugwriters.dialog_controllers

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.bugwriters.R
import org.bugwriters.ui.theme.Green

class CustomDialog(val controller: DialogController) {
    private lateinit var dialog: @Composable () -> Unit

    class Properties {
        var textSizeBody = 20.sp
        var textSizeTitle = 24.sp
        var confirmButtonColor = Green
        var declineButtonColor = Color.Red

        //String Resources
        var confirmButtonText = R.string.confirm
        var declineButtonText = R.string.decline

        var dismissOnClickOutside = true
        var dismissOnBackClick = true
        var shownText by mutableStateOf<Any>("")
            private set
        var title by mutableIntStateOf(R.string.app_name)

        private var additionalArguments = emptyArray<String>()

        fun setText(text: String): Properties {
            this.shownText = text
            return this
        }

        fun setText(@StringRes text: Int): Properties {
            this.shownText = text
            return this
        }

        fun setText(@StringRes text: Int, vararg arguments: String): Properties {
            additionalArguments = arguments.toList().toTypedArray()
            this.shownText = text
            return this
        }

    }

    fun show() {
        controller.openDialog()
    }

    fun dismiss() {
        controller.closeDialog()
    }

    fun isOpen(): Boolean {
        return controller.isOpened
    }

    val properties = Properties()

    fun setView(view: @Composable () -> Unit) {
        dialog = view
    }

    @Composable
    fun View() {
        dialog.invoke()
    }


}