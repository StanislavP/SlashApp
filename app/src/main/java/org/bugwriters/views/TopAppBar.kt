package org.bugwriters.views

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import org.bugwriters.ui.theme.Green

@Composable
fun TapAppBar(
    title: String,
    onBackClick: () -> Boolean,
    modifier: Modifier = Modifier,
    navController: NavController,
    actionButtonContent: @Composable () -> Unit = {}
) {
    var isPopBack by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(text = title, color = Color.White)
        },
        navigationIcon = {
            IconButton(onClick = {
                val boolean = onBackClick()
                if (!isPopBack && boolean) {
                    isPopBack = navController.popBackStack()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = { actionButtonContent() },
        modifier = modifier,
        backgroundColor = Green
    )
}