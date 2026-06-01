package com.firmino.geriodonto.di

import android.content.Context
import com.firmino.geriodonto.data.database.AppDatabase
import com.firmino.geriodonto.data.database.MedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideMedDao(database: AppDatabase): MedDao {
        return database.medDao()
    }
}