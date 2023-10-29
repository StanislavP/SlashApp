package org.bugwriters

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import org.bugwriters.ui.theme.Green

object GlobalProgressCircle {
    private var isVisible by mutableStateOf(false)

    fun isOpen(): Boolean {
        return isVisible
    }

    private var progress by mutableFloatStateOf(-1f)

    @Composable
    fun View() {
        val animate = animateFloatAsState(
            targetValue = progress,
            ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
        )
        BackHandler(enabled = isVisible) {

        }
        if (isVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SolidColor(Color.Black), alpha = 0.4f)
                    .clickable(
                        MutableInteractionSource(),
                        null
                    ) { },
                contentAlignment = Alignment.Center
            ) {
                if (progress >= 0.0f)
                    CircularProgressIndicator(
                        color = Green,
                        modifier = Modifier.size(50.dp),
                        progress = animate.value
                    )
                else CircularProgressIndicator(
                    color = Green,
                    modifier = Modifier.size(50.dp),
                )
            }
        }
    }


    fun show() {
        isVisible = true
    }

    fun dismiss() {
        isVisible = false
        progress = -1f
    }

}