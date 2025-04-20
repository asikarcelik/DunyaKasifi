package com.asikarcelik.dnyakaifi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.asikarcelik.dnyakaifi.data.model.Explorer
import kotlinx.coroutines.flow.Flow

@Dao
interface ExplorerDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExplorer(explorer: Explorer): Long
    
    @Update
    suspend fun updateExplorer(explorer: Explorer)
    
    @Delete
    suspend fun deleteExplorer(explorer: Explorer)
    
    @Query("SELECT * FROM explorers WHERE id = :explorerId")
    fun getExplorerById(explorerId: Long): Flow<Explorer>
    
    @Query("SELECT * FROM explorers ORDER BY lastUpdated DESC")
    fun getAllExplorers(): Flow<List<Explorer>>
    
    @Query("UPDATE explorers SET level = :level, experience = :experience, title = :title WHERE id = :explorerId")
    suspend fun updateExplorerProgress(
        explorerId: Long, 
        level: Int, 
        experience: Int, 
        title: String
    )
    
    @Query("UPDATE explorers SET visitedCountries = :visitedCountries WHERE id = :explorerId")
    suspend fun updateVisitedCountries(explorerId: Long, visitedCountries: List<String>)
    
    @Query("UPDATE explorers SET badges = :badges WHERE id = :explorerId")
    suspend fun updateBadges(explorerId: Long, badges: List<String>)
    
    @Query("UPDATE explorers SET hasCertificate = :hasCertificate WHERE id = :explorerId")
    suspend fun updateCertificateStatus(explorerId: Long, hasCertificate: Boolean)
} 