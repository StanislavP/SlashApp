package org.bugwriters

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.ui.graphics.Color
import org.bugwriters.ui.theme.Green

val customTextSelectionColors = TextSelectionColors(
    handleColor = Green, backgroundColor = Color.Blue.copy(alpha = 0.4f)
)