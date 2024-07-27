package yunuiy_hacker.ryzhaya_tetenka.composecalculator.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.data.manager.LocalUserManagerImpl
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.manager.LocalUserManager
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.usecase.AppEntryUseCase
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.usecase.ReadAppEntry
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.domain.usecase.SaveAppEntry
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.MainViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)

    @Singleton
    @Provides
    fun provideAppEntryUseCase(localUserManager: LocalUserManager): AppEntryUseCase =
        AppEntryUseCase(
            saveAppEntry = SaveAppEntry(localUserManager),
            readAppEntry = ReadAppEntry(localUserManager)
        )
}