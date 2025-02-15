package com.newmaziar.cryptopancake.crypto.data.local

import kotlinx.coroutines.flow.Flow

interface LocalDataStore {

    val currency: Flow<String>
    suspend fun saveCurrency(isUSUser: Boolean)
}