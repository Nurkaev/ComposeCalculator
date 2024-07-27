package yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.usecase

import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.manager.LocalUserManager

class SaveAppEntry(private val localUserManager: LocalUserManager) {
    suspend operator fun invoke(isDarkTheme: Boolean) {
        localUserManager.saveAppEntry(isDarkTheme)
    }
}