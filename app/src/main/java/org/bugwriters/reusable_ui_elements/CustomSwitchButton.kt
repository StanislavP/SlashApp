package org.bugwriters.reusable_ui_elements

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.bugwriters.ui.theme.Green


data class CustomSwitchButtonProperties(
    val circleColor: Color = Green,
    val borderColor: Color = Green,
    val backgroundColor: Color = Color.Transparent,
    val colorActive: Color = Color.White,
    val colorNotActive: Color = Color.Black,
    val circleAnimationSpeedMillis: Int = 300,
    val imagesColorChangeSpeedMillis: Int = (circleAnimationSpeedMillis * 0.02).toInt()
)

@Composable
fun CustomSwitchButtonTexts(
    buttonWidth: Dp,
    buttonHeight: Dp,
    initialValue: Boolean = false,
    enabled: Boolean = true,
    textLeft: String,
    textRight: String,
    properties: CustomSwitchButtonProperties = CustomSwitchButtonProperties(),
    onSwitch: (Boolean) -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val switchButton = remember {
        MutableTransitionState(initialValue)
    }


    LaunchedEffect(key1 = initialValue) {
        switchButton.targetState = initialValue
    }

    Box(modifier = Modifier
        .width(buttonWidth)
        .height(buttonHeight)
        .clip(CircleShape)
        .background(properties.backgroundColor)
        .border(2.dp, properties.borderColor, CircleShape)
        .clickable(
            interactionSource = interactionSource, indication = null, enabled = enabled
        ) {
            switchButton.targetState = !switchButton.currentState
            onSwitch(switchButton.targetState)
        }) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding()
                .background(Color.Transparent)

        ) {
            MovingCircle(buttonWidth, switchButton, properties)
        }
        SwitchButtonText(
            buttonWidth = buttonWidth,
            switch = switchButton,
            textLeft = textLeft,
            textRight = textRight,
            properties = properties
        )
    }
}

@Composable
private fun MovingCircle(
    buttonWidth: Dp,
    switch: MutableTransitionState<Boolean>,
    properties: CustomSwitchButtonProperties
) {
    val openWidth = remember {
        buttonWidth / 2
    }
    AnimatedVisibility(
        visibleState = switch, enter = expandHorizontally(
            expandFrom = Alignment.Start,
            animationSpec = tween(properties.circleAnimationSpeedMillis)
        ), exit = shrinkHorizontally(
            shrinkTowards = Alignment.Start,
            animationSpec = tween(properties.circleAnimationSpeedMillis)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(openWidth)
        )
    }
    Box(
        modifier = Modifier
            .size(buttonWidth / 2)
            .clip(CircleShape)
            .background(properties.circleColor)

    )
}

@Composable
private fun SwitchButtonText(
    buttonWidth: Dp,
    switch: MutableTransitionState<Boolean>,
    textLeft: String,
    textRight: String,
    properties: CustomSwitchButtonProperties
) {
    val colorForSearch = remember {
        Animatable(properties.colorNotActive)
    }
    val colorForItems = remember {
        Animatable(properties.colorActive)
    }
    val padding = remember {
        buttonWidth / 10
    }
    LaunchedEffect(key1 = switch.targetState) {
        if (!switch.targetState) {
            colorForItems.animateTo(
                properties.colorActive,
                tween(properties.imagesColorChangeSpeedMillis, easing = LinearEasing)
            )
            colorForSearch.animateTo(
                properties.colorNotActive,
                tween(properties.imagesColorChangeSpeedMillis, easing = LinearEasing)
            )
        } else {
            colorForSearch.animateTo(
                properties.colorActive,
                tween(properties.imagesColorChangeSpeedMillis, easing = LinearEasing)
            )
            colorForItems.animateTo(
                properties.colorNotActive,
                tween(properties.imagesColorChangeSpeedMillis, easing = LinearEasing)
            )
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = textLeft,
            modifier = Modifier
                .padding(start = padding)
                .align(Alignment.CenterStart),
            color = colorForItems.value,
            textAlign = TextAlign.Center

        )
        Text(
            text = textRight,
            modifier = Modifier
                .padding(end = padding)
                .align(Alignment.CenterEnd),
            color = colorForSearch.value,
            textAlign = TextAlign.Center

        )
    }
}
