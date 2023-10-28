package org.bugwriters.views.main_screen.client

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.bugwriters.ui.theme.Green

@Composable
fun MainScreenClientView() {
    TopAppBar(title = { Text(text = "COMPANY NAME", modifier = Modifier.fillMaxSize()) }, backgroundColor = Green, actions = {
        Icon(imageVector = Icons.Default.Add, contentDescription =null )
    })
}