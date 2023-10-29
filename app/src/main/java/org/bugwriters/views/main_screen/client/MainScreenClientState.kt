package org.bugwriters.views.main_screen.client

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bugwriters.connection.API
import org.bugwriters.connection.createRetrofitService
import org.bugwriters.connection.executeRequest
import org.bugwriters.connection.models.responses.Business
import org.bugwriters.connection.models.responses.Product

class MainScreenClientState {
    var isSearchOpen by mutableStateOf(false)
    var searchText by mutableStateOf("")
    var items = mutableStateListOf<Product>()
    private val listOFBusinesses = mutableListOf<Business>()
    val filter = mutableStateListOf<Business>()
    var expanded by mutableStateOf(true)


    fun changeFilter(value: String) {
        filter.clear()
        filter.addAll(listOFBusinesses.filter { it.name.contains(value, ignoreCase = true) })
        expanded = !filter.isEmpty()
        if (value.isBlank()) getAllItems()
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val service = createRetrofitService(API::class.java)
            getAllItems()
            executeRequest { service.getAllBusinesses() }.onSuccessSuspend {
                withContext(Dispatchers.Main) {
                    listOFBusinesses.addAll(it.clients)
                }
            }
        }
    }

    private fun getAllItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val service = createRetrofitService(API::class.java)
            executeRequest { service.getProducts() }.onSuccessSuspend {
                withContext(Dispatchers.Main) {
                    items.clear()
                    items.addAll(it.productsResponse)
                }
            }
        }
    }

    fun getAllItemsByBusiness() {
        CoroutineScope(Dispatchers.IO).launch {
            val service = createRetrofitService(API::class.java)
            executeRequest { service.getAllOffersByBusiness(listOFBusinesses.first { it.name == searchText }.id) }.onSuccessSuspend {
                withContext(Dispatchers.Main) {
                    items.clear()
                    items.addAll(it.productsResponse)
                }
            }
        }
    }
}
