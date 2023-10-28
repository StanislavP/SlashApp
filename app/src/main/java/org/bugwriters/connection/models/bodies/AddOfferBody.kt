package org.bugwriters.connection.models.bodies

import org.bugwriters.Config
import java.math.BigDecimal

enum class ProductType {
    PRODUCT, SERVICE
}

data class AddOfferBody(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val productType: ProductType,
    val id: Long = 0,
    val clientName: String = Config.email,
    val duration: BigDecimal = BigDecimal.ZERO,
    val discount: BigDecimal = BigDecimal.ZERO,
)