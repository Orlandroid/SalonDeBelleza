package com.example.data.preferences

import androidx.core.content.edit
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SecurePreferencesManager @Inject constructor(
    @ApplicationContext protected val context: Context
) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    protected val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "SecureAppPreferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    protected fun saveSecureKey(key: String, value: Any) {
        sharedPreferences.edit {
            when (value) {
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("Unsupported secure preference type")
            }
        }
    }

    protected fun getSecureString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    protected fun getSecureBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    protected fun getSecureInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    protected fun removeSecureKey(key: String) {
        sharedPreferences.edit { remove(key) }
    }

    protected fun clearSecurePreferences() {
        sharedPreferences.edit { clear() }
    }
}