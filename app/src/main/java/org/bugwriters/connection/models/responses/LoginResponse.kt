package org.bugwriters.connection.models.responses

data class LoginResponse(
    val username: String,
    val email: String,
    val roles: String?
)