package com.imageviewer.demo.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.UserRepository
import com.imageviewer.demo.framework.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val context: Context
) : UserRepository {

    private val authTokenKey = stringPreferencesKey("auth_token")

    override val isSessionExpired: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[authTokenKey].isNullOrEmpty()
    }

    override suspend fun saveLoginState(
        authToken: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[authTokenKey] = authToken
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            context.dataStore.edit { preferences ->
                preferences.remove(authTokenKey)
            }
            Result.Success(Unit)

        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }
}
