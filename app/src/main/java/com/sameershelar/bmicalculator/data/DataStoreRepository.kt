package com.sameershelar.bmicalculator.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreRepository(
    private val context: Context,
) {
    companion object {
        private val HEIGHT_KEY = intPreferencesKey("user_height")
    }

    suspend fun saveHeight(height: Int) {
        context.dataStore.edit { preferences ->
            preferences[HEIGHT_KEY] = height
        }
    }

    fun getHeight(): Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[HEIGHT_KEY] ?: 165
        }
}
