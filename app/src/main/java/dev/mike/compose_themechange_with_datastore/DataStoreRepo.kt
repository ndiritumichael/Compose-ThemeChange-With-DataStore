package dev.mike.compose_themechange_with_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreRepo(context: Context) {

    private val Context.createDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val themeValue = intPreferencesKey("theme")
    private val dataStore = context.createDataStore

    suspend fun setTheme(id: Int) {
        dataStore.edit { settings ->
            settings[themeValue] = id
        }
    }

    val currentTheme = dataStore.data.map { preferences ->
        preferences[themeValue]
    }
}

enum class AppThemes {
    MODE_AUTO,
    MODE_LIGHT,
    MODE_DARK,
}
