package org.bugwriters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Dimensions {
    var widthInDp: Dp = 0.dp
        set(value) {
            field = value
            scaleX = (360f / value.value)
            if (value.value < 360f) errorCorrectionX = 360f - value.value
            scaledWidth = (value * scaleX).value
        }
    var heightInDp: Dp = 0.dp
        set(value) {
            field = value
            scaleY = (648f / value.value)
            if (value.value < 648f) errorCorrectionY = 360f - value.value
            scaledHeight = (value * scaleY).value
        }
    var errorCorrectionX = 0f
        private set
    var errorCorrectionY = 0f
        private set
    var scaledHeight = 0f
        private set
    var scaledWidth = 0f
        private set
    var scaleX by mutableFloatStateOf(0f)
        private set
    var scaleY by mutableFloatStateOf(0f)
}