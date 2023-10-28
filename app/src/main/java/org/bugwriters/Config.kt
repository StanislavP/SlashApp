package org.bugwriters

object Config {
    var cookie = ""
    var name = ""
    var email = ""
    var role: String? = null

    fun getPreferences() {
        cookie = SharedPreferences.it.getString(SharedPreferences.cookie, "").toString()
        name = SharedPreferences.it.getString(SharedPreferences.name, "").toString()
        email = SharedPreferences.it.getString(SharedPreferences.email, "").toString()
        role = SharedPreferences.it.getString(SharedPreferences.role, "").toString()
    }

    fun remember(cookie: String, name: String, email: String, roles: String) {
        this.cookie = cookie
        this.name = name
        this.email = email
        this.role = roles
        SharedPreferences.it.edit().putString(SharedPreferences.name, name)
            .putString(SharedPreferences.email, email)
            .putString(SharedPreferences.cookie, cookie)
            .putString(SharedPreferences.role, roles)
            .apply()
    }

    fun clear() {
        cookie = ""
        name = ""
        email = ""
        role = null
        SharedPreferences.it.edit().clear().apply()
    }
}