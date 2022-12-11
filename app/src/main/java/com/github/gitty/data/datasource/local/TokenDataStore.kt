package com.github.gitty.data.datasource.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TokenDataStore @Inject constructor(
    @ApplicationContext context: Context
): ITokenDataStore {
    private val preferenceName = "GITTY"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(preferenceName)

    private val dataStore: DataStore<Preferences> by lazy {
        context.dataStore
    }

    private fun getStringData(key: String, default: String = ""): String {
        var value = ""
        runBlocking {
            dataStore.data.firstOrNull {
                value = it[stringPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }


    private fun setStringData(key: String, value: String) {
        runBlocking {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[stringPreferencesKey(key)] = value
            }
        }
    }

    override fun setToken(token: String) {
        setStringData(PRIVATE_TOKEN, token)
    }

    override fun getToken(): String {
        return getStringData(PRIVATE_TOKEN)
    }

    override fun clearToken() {
        setStringData(PRIVATE_TOKEN, "")
    }

    companion object {
        private const val PRIVATE_TOKEN = "token"
    }
}