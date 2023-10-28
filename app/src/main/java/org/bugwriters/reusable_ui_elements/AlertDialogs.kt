package org.bugwriters.reusable_ui_elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import org.bugwriters.dialog_controllers.CustomDialog
import org.bugwriters.ui.theme.Shapes

/**
 * Pop up window with title, text, and confirmation button
 * */
@Composable
fun InformationDialog(
    customDialog: CustomDialog
) {
    val controller = customDialog.controller
    val properties = customDialog.properties

    if (controller.isOpened) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = {
                controller.closeDialog()
            },
            confirmButton = {
                TextButton(onClick = {
                    controller.onConfirm()
                    controller.closeDialog()
                }) {
                    Text(
                        text = stringResource(id = properties.confirmButtonText),
                        color = properties.confirmButtonColor
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = properties.title),
                    fontSize = properties.textSizeTitle
                )
            },
            text = {
                if (properties.shownText is Int) {
                    Text(
                        text = stringResource(id = properties.shownText as Int),
                        fontSize = properties.textSizeBody
                    )
                } else {
                    Text(
                        text = properties.shownText as String,
                        fontSize = properties.textSizeBody
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = Shapes.large,
            backgroundColor = Color.White,
            properties = DialogProperties(
                dismissOnBackPress = properties.dismissOnBackClick,
                dismissOnClickOutside = properties.dismissOnClickOutside
            )
        )
    }
}

/**
 * Pop up window with title, text, and 2 buttons(decline,confirm)
 * */
@Composable
fun ConfirmDialog(
    customDialog: CustomDialog
) {
    val controller = customDialog.controller
    val properties = customDialog.properties

    if (controller.isOpened) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = {
                controller.onClickOutside()
            },
            confirmButton = {
                TextButton(onClick = {
                    controller.onConfirm()
                    controller.closeDialog()
                }) {
                    Text(
                        text = stringResource(id = properties.confirmButtonText),
                        color = properties.confirmButtonColor
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    controller.onDecline()
                    controller.closeDialog()
                }) {
                    Text(
                        text = stringResource(id = properties.declineButtonText),
                        color = properties.declineButtonColor
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = properties.title),
                    fontSize = properties.textSizeTitle
                )
            },
            text = {
                if (properties.shownText is Int) {
                    Text(
                        text = stringResource(id = properties.shownText as Int),
                        fontSize = properties.textSizeBody
                    )
                } else {
                    Text(
                        text = properties.shownText as String,
                        fontSize = properties.textSizeBody
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = Shapes.large,
            backgroundColor = Color.White,
            properties = DialogProperties(
                dismissOnBackPress = properties.dismissOnBackClick,
                dismissOnClickOutside = properties.dismissOnClickOutside
            )
        )
    }
}