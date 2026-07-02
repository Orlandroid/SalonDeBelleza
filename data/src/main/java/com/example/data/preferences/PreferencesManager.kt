package com.example.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

open class PreferencesManager @Inject constructor(
    protected val context: Context
) {

    protected val Context.dataStore by preferencesDataStore(name = "AppPreferences")


    protected suspend fun savePreferenceKey(key: String, value: Any) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                else -> throw IllegalArgumentException("Unsupported preference type")
            }
        }
    }

    protected suspend fun removePreferenceKey(key: String) {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
            preferences.remove(booleanPreferencesKey(key))
            preferences.remove(intPreferencesKey(key))
            preferences.remove(doublePreferencesKey(key))
        }
    }

}