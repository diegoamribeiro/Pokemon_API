package com.dmribeiro.pokedex_app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dmribeiro.pokedex_app.utils.Constants.USER_PREFERENCES
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private val Context.dataStorePreferences: DataStore<Preferences> by preferencesDataStore(
    USER_PREFERENCES
)

class UserPreferencesRepository @Inject constructor(@ApplicationContext context: Context) {

    private val preferencesDataStore: DataStore<Preferences> = context.dataStorePreferences

    companion object UsersPreferencesKeys {
        val SCROLL_POSITION = intPreferencesKey(name = "SCROLL_POSITION")
    }

    suspend fun saveUserData(scrollPosition: Int) {
        preferencesDataStore.edit { preferences ->
            preferences[SCROLL_POSITION] = scrollPosition
        }
    }

    val scrollPositionFlow: Flow<Int> = preferencesDataStore.data
        .catch {
            if (this is Exception) {
                emit(emptyPreferences())
            }
        }.map { username ->
            username[SCROLL_POSITION] ?: 0
        }

    suspend fun deleteAllData() {
        preferencesDataStore.edit { data ->
            data.clear()
        }
    }


}
