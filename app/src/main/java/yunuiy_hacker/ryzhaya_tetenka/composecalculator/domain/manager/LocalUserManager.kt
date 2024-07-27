package yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry(darkTheme: Boolean)

    fun readAppEntry(): Flow<Boolean>
}