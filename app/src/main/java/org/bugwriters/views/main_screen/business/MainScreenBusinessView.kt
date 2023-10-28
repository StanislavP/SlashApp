package org.bugwriters.views.main_screen.business

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.bugwriters.Screens
import org.bugwriters.models.Item
import org.bugwriters.ui.theme.Green

@Composable
fun MainScreenBusinessView(navController: NavController) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "COMPANY NAME",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            backgroundColor = Green,
            actions = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    Modifier.size(40.dp), tint = Color.White
                )
            }
        )
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            item {
                BusinessItem(
                    Item("SAPUN", "MNOGO QKA KOMPANIQ BRAT", "420.69", "NESHTO"),
                    {
                        navController.navigate(Screens.edit_offer) {
                            launchSingleTop = true
                        }
                    },
                ) {}
            }
            item {
                ClientItem(
                    Item("MNOGOQKSAPUN", "MNOGO QKA KOMPANIQ BRAT", "420.69", "NESHTO")
                ) {}
            }
        }

    }
}


@Composable
fun BusinessItem(item: Item, onClickEdit: () -> Unit, onClickDelete: () -> Unit) {
    var expand by remember {
        mutableStateOf(0.dp)
    }
    Column(
        Modifier
            .shadow(5.dp)
            .padding(10.dp)
            .clickable(remember {
                MutableInteractionSource()
            }, null) {
                expand = if (expand == 0.dp)
                    100.dp
                else 0.dp
            }
    ) {
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .height(200.dp)

        ) {
            val maxWidth = maxWidth
            Row {
                Column(
                    Modifier
                        .width(maxWidth * 0.60f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = item.name, fontSize = 24.sp)
                    Text(text = "From company: ${item.companyName}", fontSize = 21.sp)
                    Text(text = "Price: ${item.price}", fontSize = 21.sp)
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(maxWidth * 0.20f)
                        .background(Green)
                        .clickable { onClickEdit() }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(maxWidth * 0.20f)
                        .background(Color.Red)
                        .clickable { onClickDelete() }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White
                    )
                }


            }
        }
        Box(
            modifier = Modifier
                .height(animateDpAsState(targetValue = expand, label = "").value)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(text = "Description: ${item.description}", fontSize = 21.sp)
        }
    }
}

@Composable
fun ClientItem(item: Item, onAdd: () -> Unit) {
    var expand by remember {
        mutableStateOf(0.dp)
    }
    Column(
        Modifier
            .shadow(5.dp)
            .padding(10.dp)
            .clickable(remember {
                MutableInteractionSource()
            }, null) {
                expand = if (expand == 0.dp)
                    100.dp
                else 0.dp
            }
    ) {
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .height(200.dp)

        ) {
            val maxWidth = maxWidth
            Row {
                Column(
                    Modifier
                        .width(maxWidth * 0.80f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = item.name, fontSize = 24.sp)
                    Text(text = "From company: ${item.companyName}", fontSize = 21.sp)
                    Text(text = "Price: ${item.price}", fontSize = 21.sp)
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(maxWidth * 0.20f)
                        .background(Green)
                        .clickable { onAdd() }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .height(animateDpAsState(targetValue = expand, label = "").value)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(text = "Description: ${item.description}", fontSize = 21.sp)
        }
    }
}