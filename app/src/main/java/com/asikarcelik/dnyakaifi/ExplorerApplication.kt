package com.asikarcelik.dnyakaifi

import android.app.Application
import com.asikarcelik.dnyakaifi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ExplorerApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Koin DI'yi başlat
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@ExplorerApplication)
            modules(appModule)
        }
    }
} 