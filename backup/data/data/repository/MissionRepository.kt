package com.asikarcelik.dnyakaifi.data.repository

import com.asikarcelik.dnyakaifi.data.local.dao.MissionDao
import com.asikarcelik.dnyakaifi.data.model.Mission
import com.asikarcelik.dnyakaifi.data.model.MissionType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Görev verilerini yöneten repository sınıfı
 */
@Singleton
class MissionRepository @Inject constructor(
    private val missionDao: MissionDao
) {
    /**
     * Tüm görevleri akış olarak getirir
     */
    fun getAllMissions(): Flow<List<Mission>> = missionDao.getAllMissions()
    
    /**
     * ID'ye göre bir görevi akış olarak getirir
     */
    fun getMissionById(missionId: String): Flow<Mission> = missionDao.getMissionById(missionId)
    
    /**
     * Ülke koduna göre görevleri akış olarak getirir
     */
    fun getMissionsByCountry(countryCode: String): Flow<List<Mission>> = 
        missionDao.getMissionsByCountry(countryCode)
    
    /**
     * Görev türüne göre görevleri akış olarak getirir
     */
    fun getMissionsByType(missionType: MissionType): Flow<List<Mission>> = 
        missionDao.getMissionsByType(missionType)
    
    /**
     * Tamamlanmış görevleri akış olarak getirir
     */
    fun getCompletedMissions(): Flow<List<Mission>> = missionDao.getCompletedMissions()
    
    /**
     * Aktif görevleri akış olarak getirir
     */
    fun getActiveMissions(): Flow<List<Mission>> = missionDao.getActiveMissions()
    
    /**
     * Tamamlanmış görev sayısını akış olarak getirir
     */
    fun getCompletedMissionsCount(): Flow<Int> = missionDao.getCompletedMissionsCount()
    
    /**
     * Birden fazla görevi veritabanına ekler
     */
    suspend fun insertMissions(missions: List<Mission>) {
        missionDao.insertMissions(missions)
    }
    
    /**
     * Tek bir görevi veritabanına ekler
     */
    suspend fun insertMission(mission: Mission) {
        missionDao.insertMission(mission)
    }
    
    /**
     * Bir görevi günceller
     */
    suspend fun updateMission(mission: Mission) {
        missionDao.updateMission(mission)
    }
    
    /**
     * Bir görevin tamamlanma durumunu günceller
     */
    suspend fun updateMissionCompletionStatus(
        missionId: String, 
        isCompleted: Boolean, 
        completionDate: Long? = if (isCompleted) System.currentTimeMillis() else null
    ) {
        missionDao.updateMissionCompletionStatus(missionId, isCompleted, completionDate)
    }
    
    /**
     * Bir görevin aktiflik durumunu günceller
     */
    suspend fun updateMissionActiveStatus(missionId: String, isActive: Boolean) {
        missionDao.updateMissionActiveStatus(missionId, isActive)
    }
} 