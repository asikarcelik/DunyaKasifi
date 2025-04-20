package com.asikarcelik.dnyakaifi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asikarcelik.dnyakaifi.data.local.converter.LandmarkListConverter
import com.asikarcelik.dnyakaifi.data.local.converter.ListStringConverter
import com.asikarcelik.dnyakaifi.data.local.converter.MapStringConverter
import com.asikarcelik.dnyakaifi.data.local.converter.QuizQuestionListConverter
import com.asikarcelik.dnyakaifi.data.local.dao.CountryDao
import com.asikarcelik.dnyakaifi.data.local.dao.ExplorerDao
import com.asikarcelik.dnyakaifi.data.local.dao.MissionDao
import com.asikarcelik.dnyakaifi.data.model.Country
import com.asikarcelik.dnyakaifi.data.model.Explorer
import com.asikarcelik.dnyakaifi.data.model.Mission

/**
 * Uygulama veritabanı sınıfı
 */
@Database(
    entities = [Explorer::class, Country::class, Mission::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ListStringConverter::class,
    MapStringConverter::class,
    LandmarkListConverter::class,
    QuizQuestionListConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun explorerDao(): ExplorerDao
    abstract fun countryDao(): CountryDao
    abstract fun missionDao(): MissionDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dunya_kasifi_database"
                )
                    .fallbackToDestructiveMigration() // Geliştirme aşamasında şema değişikliklerinde veriyi siler
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 