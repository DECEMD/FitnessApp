package com.fitnesskit.fitnessappsample.presentation.di

import android.content.Context
import com.fitnesskit.domain.Repository
import com.fitnesskit.domain.ScheduleInteractor
import com.fitnesskit.domain.ScheduleInteractorInterface
import com.fitnesskit.fitnessappsample.data.RepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.InnerAppModule::class])
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Module
    interface InnerAppModule {

        @Binds
        @Singleton
        fun provideRepository(repository: RepositoryImplementation): Repository

        @Singleton
        @Binds
        fun provideScheduleInteractor(scheduleInteractor: ScheduleInteractor): ScheduleInteractorInterface

    }
}