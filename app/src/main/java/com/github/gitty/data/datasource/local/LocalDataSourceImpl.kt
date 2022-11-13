package com.github.gitty.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
): LocalDataSource {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    override fun saveToken(token: String) {
        sharedPrefs.edit {
            putString(PRIVATE_TOKEN, token)
        }
    }

    override fun getToken(): String? {
        return sharedPrefs.getString(PRIVATE_TOKEN, null)
    }

    override fun clearToken() {
        sharedPrefs.edit {
            putString(PRIVATE_TOKEN, null)
        }
    }

    companion object {
        private const val PRIVATE_TOKEN = "token"
    }
}