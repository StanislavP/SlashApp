package org.bugwriters.views.shopping_cart

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bugwriters.GlobalProgressCircle
import org.bugwriters.R
import org.bugwriters.ShoppingCart
import org.bugwriters.paymentprovider.stripe.StripeHelper
import org.bugwriters.reusable_ui_elements.ViewHolder
import org.bugwriters.ui.theme.Green
import org.bugwriters.views.main_screen.client.checkout.CheckoutViewModel
import java.math.RoundingMode

@Composable
fun ShoppingCartView(navController: NavController) {
    val viewModel = viewModel {
        CheckoutViewModel()
    }
    LaunchedEffect(key1 = StripeHelper.paymentState.value) {
        when (StripeHelper.paymentState.value) {
            StripeHelper.PaymentStates.CANCELED -> {
                GlobalProgressCircle.dismiss()
            }

            StripeHelper.PaymentStates.FAILED -> {
                GlobalProgressCircle.dismiss()
            }

            StripeHelper.PaymentStates.COMPLETED -> {
                ShoppingCart.clearCart()
                GlobalProgressCircle.dismiss()
                navController.popBackStack()
                StripeHelper.reset()
            }

            else -> {}
        }
    }
    ViewHolder {
        TopAppBar(
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            navController.popBackStack()
                        }, tint = Color.White
                )
            },
            title = {
                Text(
                    text = "Shopping Cart",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            backgroundColor = Green,
            actions = {
                Text(
                    text = "Total: " + ShoppingCart.totalAmount.value.setScale(
                        2,
                        RoundingMode.HALF_UP
                    ),
                    fontSize = 18.sp,
                    color = Color.White,

                    )
            }
        )
        Box(modifier = Modifier.padding(5.dp)) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
            ) {

                items(ShoppingCart.items) {
                    ShoppingCartItem(it) { item -> ShoppingCart.removeItem(item) }
                }
            }
            FloatingActionButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.pay()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(70.dp),
                shape = CircleShape,
                containerColor = Green
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.money),
                    contentDescription = null,
                    tint = Color.White
                )

            }

        }
    }
}

@Composable
fun ShoppingCartItem(item: org.bugwriters.Item, onRemove: (org.bugwriters.Item) -> Unit) {
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
                .height(100.dp)

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
                    Text(text = item.amount.toString(), fontSize = 24.sp)
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(maxWidth * 0.20f), contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Red, RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onRemove(item) }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}