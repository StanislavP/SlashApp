package org.bugwriters.connection.models.bodies

enum class Roles{
    ROLE_CLIENT,ROLE_BUSINESS
}

data class RegisterBody(val name:String, val email:String, val password:String, val role: List<Roles>)

data class LoginBody(val username:String, val password:String)