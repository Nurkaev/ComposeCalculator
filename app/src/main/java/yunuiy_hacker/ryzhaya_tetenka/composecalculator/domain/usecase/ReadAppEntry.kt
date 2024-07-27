package yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.usecase

import kotlinx.coroutines.flow.Flow
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.manager.LocalUserManager

class ReadAppEntry(private val localUserManager: LocalUserManager) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}