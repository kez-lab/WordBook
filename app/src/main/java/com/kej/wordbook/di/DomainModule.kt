package com.kej.wordbook.di

import com.kej.wordbook.data.RepositoryImpl
import com.kej.wordbook.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun provideRepositoryImpl(repositoryImpl: RepositoryImpl): Repository
}