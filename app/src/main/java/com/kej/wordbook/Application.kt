package com.kej.wordbook

import android.app.Application
import com.kej.wordbook.data.database.AppDatabase

class Application: Application() {

    companion object {
        lateinit var database:AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
    }
}