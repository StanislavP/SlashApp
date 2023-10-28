package org.bugwriters.views.main_screen.client

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import org.bugwriters.ui.theme.Green

@Composable
fun MainScreenClientView() {
    TopAppBar(title = { Text(text = "COMPANY NAME") }, backgroundColor = Green)
}