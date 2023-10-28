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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.bugwriters.ui.theme.Green

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

@Composable
fun Card(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .shadow(5.dp, spotColor = Color.Black, ambientColor = Green)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width + 80, placeable.height + 80) {
                    placeable.placeRelative(40, 40)
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}