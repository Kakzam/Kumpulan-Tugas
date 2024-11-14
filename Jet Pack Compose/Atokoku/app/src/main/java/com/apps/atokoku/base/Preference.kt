package com.apps.atokoku.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

open class Preference {

    companion object {

        private lateinit var pref: SharedPreferences
        private lateinit var context: Context
        private const val APP = "ATOKOKU_"

        private var USERNAME = APP + "USERNAME"
        private var PASSWORD = APP + "PASSWORD"
        private var NAME = APP + "NAME"
        private var ID = APP + "ID"
        private var ID_TYPE = APP + "ID_TYPE"

        fun init(context: Context) = apply {
            this.context = context
        }

        fun build() {
            this.pref = context.getSharedPreferences(APP, Context.MODE_PRIVATE)
        }

        fun removeAllData() {
            pref.edit().clear().apply()
        }

        /* USER --------------------------------------------------------------------------------- */

        fun setUsername(username: String) {
            pref.edit().putString(USERNAME, username).apply()
        }

        fun getUsername(): String? {
            return pref.getString(USERNAME, "")
        }

        fun setPassword(password: String) {
            pref.edit().putString(PASSWORD, password).apply()
        }

        fun getPassword(): String? {
            return pref.getString(PASSWORD, "")
        }

        fun setName(name: String) {
            pref.edit().putString(NAME, name).apply()
        }

        fun getName(): String? {
            return pref.getString(NAME, "")
        }

        fun setId(regId: String) {
            pref.edit().putString(ID, regId).apply()
        }

        fun getId(): String? {
            return pref.getString(ID, "")
        }

        fun setIdType(regId: String) {
            pref.edit().putString(ID_TYPE, regId).apply()
        }

        fun getIdType(): String? {
            return pref.getString(ID_TYPE, "")
        }

    }
}