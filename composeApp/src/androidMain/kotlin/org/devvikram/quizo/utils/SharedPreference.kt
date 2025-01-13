package org.devvikram.quizo.utils

import android.content.Context


class SharedPreference(
    context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)

    fun saveUser(username: String, useremail: String, userpassword: String) {
        with(sharedPreferences.edit()) {
            putString(USER_NAME, username)
            putString(USER_EMAIL, useremail)
            putBoolean(IS_LOGGED_IN,true)
            apply()
        }
    }
    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }
    fun getUsername(): String? {
        return sharedPreferences.getString(USER_NAME, null)
    }
    fun getEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL, null)
    }

    fun clearUser() {
        with(sharedPreferences.edit()) {
            remove(USER_NAME)
            remove(USER_EMAIL)
            putBoolean(IS_LOGGED_IN,false)
            apply()
        }
    }


    companion object {
        private const val SHARED_PREFERENCE = "sharedPreference"
        private const val USER_NAME = "username"
        private const val USER_EMAIL = "useremail"
        private const val IS_LOGGED_IN = "isloggedin"
    }
}