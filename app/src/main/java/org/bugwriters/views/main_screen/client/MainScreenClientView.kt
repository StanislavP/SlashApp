package org.bugwriters.views.main_screen.client

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import org.bugwriters.Config
import org.bugwriters.GlobalLogoutDialog
import org.bugwriters.Item
import org.bugwriters.R
import org.bugwriters.Screens
import org.bugwriters.ShoppingCart
import org.bugwriters.reusable_ui_elements.TextField
import org.bugwriters.reusable_ui_elements.ViewHolder
import org.bugwriters.ui.theme.Green
import org.bugwriters.views.main_screen.business.ClientItem

@Composable
fun MainScreenClientView(navController: NavController) {
    val state = remember { MainScreenClientState() }
    ViewHolder {
        TopAppBar(
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = null,
                    Modifier
                        .size(40.dp)
                        .clickable {
                            GlobalLogoutDialog.show()
                        }, tint = Color.White
                )
            },
            title = {

                AnimatedVisibility(visible = !state.isSearchOpen) {
                    Text(
                        text = Config.name,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                AnimatedVisibility(visible = state.isSearchOpen) {
                    Box(modifier = Modifier.background(Color.White, RoundedCornerShape(50.dp))) {
                        TextField(
                            it,
                            text = state.searchText,
                            modifier = Modifier.fillMaxWidth(),
                            label = "",
                            onValueChange = {
                                state.searchText = it
                                state.changeFilter(it)
                            }
                        )

                    }
                    DropdownMenu(
                        expanded = state.expanded,
                        onDismissRequest = { state.expanded = false },
                        modifier = Modifier.heightIn(
                            Dp.Unspecified, 300.dp
                        ), properties = PopupProperties(clippingEnabled = false)
                    ) {
                        for (item in state.filter)
                            DropdownMenuItem(onClick = {
                                state.searchText = item.name
                                state.getAllItemsByBusiness()
                            }) {
                                Text(text = item.name)
                            }
                    }

                }

            },
            backgroundColor = Green,
            actions = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    Modifier
                        .size(40.dp)
                        .clickable {
                            state.isSearchOpen = !state.isSearchOpen
                        }, tint = Color.White
                )
            }
        )
        Box(modifier = Modifier.padding(5.dp)) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
            ) {

                items(state.items) {
                    ClientItem(it.toItem()) { item ->
                        ShoppingCart.addItem(
                            Item(
                                item.price.toBigDecimal(),
                                it.name
                            )
                        )
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.shopping_cart) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(70.dp),
                shape = CircleShape,
                containerColor = Green
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.White
                )

            }

        }
    }
}