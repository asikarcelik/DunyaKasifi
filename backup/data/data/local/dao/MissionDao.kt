package com.asikarcelik.dnyakaifi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.asikarcelik.dnyakaifi.data.model.Mission
import com.asikarcelik.dnyakaifi.data.model.MissionType
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissions(missions: List<Mission>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(mission: Mission)
    
    @Update
    suspend fun updateMission(mission: Mission)
    
    @Query("SELECT * FROM missions WHERE id = :missionId")
    fun getMissionById(missionId: String): Flow<Mission>
    
    @Query("SELECT * FROM missions")
    fun getAllMissions(): Flow<List<Mission>>
    
    @Query("SELECT * FROM missions WHERE countryCode = :countryCode")
    fun getMissionsByCountry(countryCode: String): Flow<List<Mission>>
    
    @Query("SELECT * FROM missions WHERE type = :missionType")
    fun getMissionsByType(missionType: MissionType): Flow<List<Mission>>
    
    @Query("SELECT * FROM missions WHERE isCompleted = 1")
    fun getCompletedMissions(): Flow<List<Mission>>
    
    @Query("SELECT * FROM missions WHERE isActive = 1")
    fun getActiveMissions(): Flow<List<Mission>>
    
    @Query("UPDATE missions SET isCompleted = :isCompleted, completionDate = :completionDate WHERE id = :missionId")
    suspend fun updateMissionCompletionStatus(
        missionId: String, 
        isCompleted: Boolean, 
        completionDate: Long?
    )
    
    @Query("UPDATE missions SET isActive = :isActive WHERE id = :missionId")
    suspend fun updateMissionActiveStatus(missionId: String, isActive: Boolean)
    
    @Query("SELECT COUNT(*) FROM missions WHERE isCompleted = 1")
    fun getCompletedMissionsCount(): Flow<Int>
} 