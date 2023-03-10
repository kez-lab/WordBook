package com.kej.wordbook.di

import android.content.Context
import androidx.room.Room
import com.kej.wordbook.data.database.AppDatabase
import com.kej.wordbook.data.dao.WordDao
import com.kej.wordbook.data.paging.WordPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): WordDao {
        return database.wordDao()
    }

    @Provides
    @Singleton
    fun provideWordPagingSource(wordDao: WordDao):WordPagingSource {
        return WordPagingSource(wordDao)
    }
}