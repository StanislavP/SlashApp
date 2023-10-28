package org.bugwriters.connection.models.responses

import org.bugwriters.connection.models.bodies.ProductType
import org.bugwriters.models.Item
import java.math.BigDecimal
import java.math.RoundingMode

data class ProductsResponse(val productsResponse: List<Product>) : BasicResponse()


data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val type: ProductType,
    val price: BigDecimal,
    val ownerName: String
):BasicResponse() {
    fun toItem(): Item {
        return Item(
            name,
            ownerName,
            price.setScale(2, RoundingMode.HALF_UP).toString(),
            description
        )
    }

}