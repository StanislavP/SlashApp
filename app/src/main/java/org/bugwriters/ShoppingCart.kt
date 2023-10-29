package org.bugwriters

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import org.bugwriters.paymentprovider.stripe.toCurrencyLong
import java.math.BigDecimal
import java.math.RoundingMode

object ShoppingCart {

    val items: MutableList<Item> = mutableStateListOf()
    val totalAmount = mutableStateOf(getFullAmount().toBigDecimal())
    fun clearCart() {
        items.clear()
        totalAmount.value = getFullAmountString().toBigDecimal()
    }

    fun addItem(item: Item) {
        items.add(item)
        totalAmount.value = getFullAmountString().toBigDecimal()
    }

    fun removeItem(item: Item) {
        items.remove(item)
        totalAmount.value = getFullAmountString().toBigDecimal()
    }

    fun getFullAmount(): Long {
        return items.sumOf { it.amount }.toCurrencyLong()
    }

    fun getFullAmountString(): String {
        return items.sumOf { it.amount }.setScale(2, RoundingMode.HALF_UP).toString()
    }
}

data class Item(
    val amount: BigDecimal,
    val name: String
)