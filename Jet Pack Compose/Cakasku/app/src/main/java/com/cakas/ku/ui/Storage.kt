package com.cakas.ku.ui

import android.content.Context
import android.content.SharedPreferences

open class Storage {

    companion object {

        private lateinit var pref: SharedPreferences
        private lateinit var context: Context
        private const val PREF_NAME = "cakas-pref"
        private const val APP = "CAKAS_"

        private var USER_TOKEN = APP + "USER_TOKEN"

        fun init(context: Context) = apply {
            this.context = context
        }

        fun build() {
            this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        fun removeAllData() {
            pref.edit().clear().apply()
        }

        /* USER TOKEN --------------------------------------------------------------------------- */

        fun setToken(token: String) {
            pref.edit().putString(USER_TOKEN, token).apply()
        }

        fun getToken(): String? {
            return pref.getString(USER_TOKEN, "EMPTY")
        }
    }

}