package org.bugwriters

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {
    lateinit var it:SharedPreferences
    const val name ="NAME"
    const val email ="EMAIL"
    const val cookie ="COOKIE"
    const val role ="ROLE"
    fun init(context: Context) {
        it = context.getSharedPreferences("options",Context.MODE_PRIVATE)
    }
}