package org.bugwriters.views.main_screen.client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.bugwriters.Item
import org.bugwriters.Screens
import org.bugwriters.ShoppingCart
import org.bugwriters.ui.theme.Green
import java.math.BigDecimal

@Composable
fun MainScreenClientView(navController: NavController) {

    val items = listOf<Item>(
        Item(BigDecimal("0.05"), "Test 05"),
        Item(BigDecimal("0.10"), "Test 10"),
        Item(BigDecimal("0.50"), "Test 50"),
        Item(BigDecimal("1.00"), "Test 100"),
        Item(BigDecimal("5.00"), "Test 500"),
        Item(BigDecimal("10.00"), "Test 1000"),
        Item(BigDecimal("50.00"), "Test 5000"),
        Item(BigDecimal("100.00"), "Test 10000"),
        Item(BigDecimal("500.00"), "Test 50000"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "COMPANY NAME")
                },
                backgroundColor = Green
            )
        }
    ) { innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(ShoppingCart.totalAmount.value.toPlainString())
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                modifier = Modifier.padding(innerPadding)
            ){
                items(items){item ->
                    GridItem(item = item)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { ShoppingCart.clearCart() }) {
                Text("Clear cart")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                navController.navigate(Screens.checkout)
            } ) {
                Text("Checkout")
            }
        }
    }
}


@Composable
fun GridItem(item: Item) {
    Button(onClick = { ShoppingCart.addItem(item) }) {
        Text(item.name)
    }
}