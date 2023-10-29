package org.bugwriters.reusable_ui_elements

import androidx.compose.animation.Animatable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bugwriters.ui.theme.Shapes


class Ripple(private val color: Color) : RippleTheme {
    @Composable
    override fun defaultColor(): Color {
        return color
    }

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.5f, 0.5f, 0.5f, 0.5f)
}

@Composable
private fun ButtonDp(
    buttonColor: Color,
    onclick: () -> Unit,
    rowScope: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    rippleColor: Color = Color.DarkGray,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource,
    shape: Shape = Shapes.large
) {
    CompositionLocalProvider(LocalRippleTheme provides Ripple(rippleColor)) {
        Button(
            onClick = onclick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor,
                disabledBackgroundColor = Color.DarkGray,
            ),
            modifier = modifier,
            content = rowScope,
            contentPadding = contentPadding,
            enabled = enabled,
            shape = shape,
            interactionSource = interactionSource,
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}

@Composable
fun BasicButton(
    label: String,
    buttonColor: Color,
    enabled: Boolean = true,
    width: Dp = 100.dp,
    height: Dp = 50.dp,
    rippleColor: Color = Color.DarkGray,
    labelColor: Color = Color.White,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    shape: Shape = Shapes.large,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    onclick: () -> Unit
) =
    ButtonDp(
        buttonColor = buttonColor,
        onclick = onclick,
        modifier = Modifier.size(
            width = width,
            height = height
        ),
        rowScope = {
            Text(
                text = label,
                color = labelColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        enabled = enabled,
        contentPadding = contentPadding,
        rippleColor = rippleColor,
        shape = shape,
        interactionSource = interactionSource
    )

@Composable
fun TextButton(
    label: String,
    color: Color,
    onclickColor: Color,
    enabled: Boolean = true,
    size: TextUnit = 24.sp,
    onclick: () -> Unit,
) {
    val animation = remember {
        Animatable(color)
    }
    Text(
        text = label,
        color = if (enabled) animation.value else Color.Gray,
        fontSize = size,
        modifier = Modifier.then(
            if (enabled) Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(250)
                            onclick()
                        }
                    },
                    onPress = {
                        animation.animateTo(onclickColor)
                        tryAwaitRelease()
                        animation.animateTo(color)
                    }
                )
            } else Modifier
        )
    )

}