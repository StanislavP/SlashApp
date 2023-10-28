package org.bugwriters.reusable_ui_elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.bugwriters.R
import org.bugwriters.ui.theme.Green

@Composable
fun TextField(
    focusRequester: FocusRequester?,
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backGroundColor: Color = Green,
    disabledBackGroundColor: Color = Color.Gray,
    width: Dp = 320.dp,
    height: Dp = 50.dp,
    textSize: TextUnit = 16.sp,
    textAlign: TextAlign = TextAlign.End,
    textColor: Color = Color.Black,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorText: String = "Error Text",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var maxWidth by remember { mutableStateOf<Dp?>(null) }
    var float by remember {
        mutableFloatStateOf(maxWidth?.value ?: 0.dp.value)
    }
    var showError by remember {
        mutableStateOf(false)
    }
    BasicTextField(
        value = text,
        onValueChange = {
            if (showError) {
                showError = false
            }
            onValueChange(it)
        },
        textStyle = TextStyle(
            fontSize = textSize, color = textColor,
            textAlign = textAlign,
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions, keyboardActions = keyboardActions,
        modifier = Modifier
            .size(width, height)
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
            .onFocusChanged {
                if (it.isFocused) {
                    float = 0f
                } else {
                    if (text.isBlank()) {
                        float = maxWidth?.value ?: 0.dp.value
                    }
                }
            }
            .then(
                if (enabled) Modifier.border(
                    2.dp, backGroundColor, RoundedCornerShape(50.dp)
                ) else Modifier.border(
                    2.dp, disabledBackGroundColor, RoundedCornerShape(50.dp)
                )
            )
            .then(modifier),
        singleLine = true,
        enabled = enabled,
        cursorBrush = SolidColor(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(10.dp)
                    .offset(animateFloatAsState(targetValue = float, label = "").value.dp),
            ) {
                Text(
                    text = label,
                    color = Color.Gray,
                    modifier = Modifier.layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        maxWidth =
                            (width - 20.dp) - placeable.width.toDp()
                        float = maxWidth!!.value
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    })
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
            ) {
                BoxWithConstraints(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    it()
                }
            }
        }
        AnimatedVisibility(visible = isError) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
            ) {
                Text(
                    text = errorText,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}