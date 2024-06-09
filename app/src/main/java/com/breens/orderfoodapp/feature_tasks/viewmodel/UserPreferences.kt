package com.breens.orderfoodapp.feature_tasks.viewmodel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUser(private val context: Context) {

    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserEmail")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_PASSWORD_KEY = stringPreferencesKey("user_password")
        val CHECK_PRICE = intPreferencesKey("check_price")
    }

    // to get the email
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }
    val getPassword: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_PASSWORD_KEY] ?: ""
        }
    val getPrice: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[CHECK_PRICE]
        }
    // to save the email
    suspend fun saveEmail(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = name
        }
    }
    suspend fun savePassword(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PASSWORD_KEY] = name
        }
    }
    suspend fun check_price(price: Int) {
        context.dataStore.edit { preferences ->
            preferences[CHECK_PRICE] = price
        }
    }
}






