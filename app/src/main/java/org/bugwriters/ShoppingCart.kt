package org.bugwriters

import androidx.compose.runtime.mutableStateOf
import org.bugwriters.paymentprovider.stripe.toCurrencyLong
import java.math.BigDecimal

object ShoppingCart {

    private val items: MutableList<Item> = mutableListOf()
    val totalAmount = mutableStateOf(getFullAmount().toBigDecimal())
    fun clearCart() {
        items.clear()
        totalAmount.value = getFullAmount().toBigDecimal()
    }

    fun addItem(item: Item) {
        items.add(item)
        totalAmount.value = getFullAmount().toBigDecimal()
    }

    fun getFullAmount(): Long {
        return items.sumOf { it.amount }.toCurrencyLong()
    }
}

data class Item (
    val amount: BigDecimal,
    val name: String
)