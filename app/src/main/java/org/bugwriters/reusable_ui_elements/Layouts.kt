package org.bugwriters.reusable_ui_elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun ViewHolder(
    horizontal: Alignment.Horizontal = Alignment.Start,
    vertical: Arrangement.Vertical = Arrangement.Top,
    content: @Composable ColumnScope.(focusRequester: FocusRequester) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    Column(
        Modifier
            .fillMaxSize()
            .clickable(remember {
                MutableInteractionSource()
            }, null) { focusManager.clearFocus() },
        horizontalAlignment = horizontal,
        verticalArrangement = vertical
    ) { content(focusRequester) }
}