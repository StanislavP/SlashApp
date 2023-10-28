package org.bugwriters.connection.models.responses

data class BusinessesResponse(val clients: List<Business>) : BasicResponse()
data class Business(val id: Long, val name: String)