package yunuiy_hacker.ryzhaya_tetenka.composecalculator.data.manager

import android.content.Context
import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.manager.LocalUserManager
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.util.Constants
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.util.Constants.APP_ENTRY
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.util.Constants.USER_SETTINGS

class LocalUserManagerImpl(private val context: Context) : LocalUserManager {
    override suspend fun saveAppEntry(darkTheme: Boolean) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = darkTheme
            Log.e("IsDarkThemeDataStore", darkTheme.toString())
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

private object PreferencesKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}