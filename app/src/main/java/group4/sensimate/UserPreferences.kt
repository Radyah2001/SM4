package group4.sensimate

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences (private val context: Context){

    companion object {
        private val Context.SensiMateDataStore: DataStore<Preferences> by preferencesDataStore("MyStorage")
        val role = stringPreferencesKey("role")
    }

    //get the saved Role
    val getRole: Flow<String?> = context.SensiMateDataStore.data
        .map { preferences ->
            preferences[role] ?: "ss"
        }
    //save role into datastore
    suspend fun saveRole(newRole: String) {
        context.SensiMateDataStore.edit { preferences ->
            preferences[role] = newRole
        }
    }

    suspend fun clear(){
        context.SensiMateDataStore.edit { preferences ->
            preferences.clear()
        }
    }

}