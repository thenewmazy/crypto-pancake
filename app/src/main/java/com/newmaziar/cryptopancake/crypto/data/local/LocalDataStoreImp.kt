package com.newmaziar.cryptopancake.crypto.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val USD = "USD"
const val SEK = "SEK"
const val CURRENCY = "currency"
const val PREF_NAME = "settings"

class LocalDataStoreImp(private val context: Context) : LocalDataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREF_NAME
    )
    private val CURRENCY_KEY = stringPreferencesKey(CURRENCY)

    override val currency: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CURRENCY_KEY] ?: USD
    }

    override suspend fun saveCurrency(isUSUser: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[CURRENCY_KEY] = if (isUSUser) USD else SEK
        }
    }

}