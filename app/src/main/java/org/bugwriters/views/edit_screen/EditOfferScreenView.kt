package org.bugwriters.views.edit_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.connection.models.bodies.ProductType
import org.bugwriters.reusable_ui_elements.BasicButton
import org.bugwriters.reusable_ui_elements.Card
import org.bugwriters.reusable_ui_elements.TextField
import org.bugwriters.reusable_ui_elements.ViewHolder
import org.bugwriters.ui.theme.Green
import org.bugwriters.reusable_ui_elements.CustomSwitchButtonTexts

enum class ViewType(val title: String) {
    ADD("Add"), EDIT("Edit")
}


@Composable
fun EditOfferScreenView(type: ViewType, navController: NavController, id: Long?) {
    val state = remember {
        EditScreenState(navController = navController, id)
    }
    val dp10 = remember {
        10.dp
    }

    val dp40 = remember {
        40.dp
    }
    ViewHolder(Alignment.CenterHorizontally, Arrangement.Center) {
        if (state.isLoading) GlobalProgressCircle.show()
        if (!state.isLoading) GlobalProgressCircle.dismiss()
        AnimatedVisibility(visible = !state.isLoading) {
            Card {
                Text(text = type.title, fontSize = 24.sp, color = Green)
                Spacer(modifier = Modifier.height(dp40))
                NameField(state, it)
                Spacer(modifier = Modifier.height(dp10))
                PriceField(state, it)
                Spacer(modifier = Modifier.height(dp10))
                DescriptionField(state, it)
                Spacer(modifier = Modifier.height(dp10))
                OfferType(state)
                Spacer(modifier = Modifier.height(dp40))
                BasicButton("Save", Green, enabled = !state.isError.value) {
                    if (type == ViewType.ADD) state.addItem() else state.editItem()
                }

            }
        }
    }
}

@Composable
private fun NameField(state: EditScreenState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Name",
        state.name,
        { value -> state.name = value },
        isError = state.isErrorName.value,
        errorText = state.errorMassageName,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
    )
}

@Composable
private fun OfferType(state: EditScreenState) {
    CustomSwitchButtonTexts(
        buttonWidth = 320.dp,
        initialValue = state.type == ProductType.PRODUCT,
        buttonHeight = 50.dp,
        onSwitch = {
            if (it) state.type = ProductType.PRODUCT else state.type = ProductType.SERVICE
        },
        textLeft = "Service",
        textRight = "Product"
    )
}

@Composable
private fun PriceField(state: EditScreenState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Price",
        state.price,
        { value -> state.price = value },
        isError = state.isErrorPrice.value,
        errorText = state.errorMassagePrice,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
    )
}

@Composable
private fun DescriptionField(state: EditScreenState, focusRequester: FocusRequester) {
    TextField(
        focusRequester = focusRequester,
        "Description",
        state.description,
        { value -> if (value.length < 200) state.description = value },
        roundedCorners = 5.dp,
        maxLines = 3,
        height = 100.dp,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    )
}