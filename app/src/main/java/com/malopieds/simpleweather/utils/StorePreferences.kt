package com.malopieds.simpleweather.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import java.util.prefs.Preferences

class StorePreferences(val context: Context) {
    companion object {
        val USER_AGE_KEY = stringPreferencesKey("test")
    }

}