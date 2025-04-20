package com.asikarcelik.dnyakaifi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.asikarcelik.dnyakaifi.data.model.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<Country>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)
    
    @Update
    suspend fun updateCountry(country: Country)
    
    @Query("SELECT * FROM countries WHERE code = :countryCode")
    fun getCountryByCode(countryCode: String): Flow<Country>
    
    @Query("SELECT * FROM countries")
    fun getAllCountries(): Flow<List<Country>>
    
    @Query("SELECT * FROM countries WHERE continent = :continent")
    fun getCountriesByContinent(continent: String): Flow<List<Country>>
    
    @Query("SELECT * FROM countries WHERE unlocked = 1")
    fun getUnlockedCountries(): Flow<List<Country>>
    
    @Query("UPDATE countries SET unlocked = :isUnlocked WHERE code = :countryCode")
    suspend fun updateUnlockStatus(countryCode: String, isUnlocked: Boolean)
    
    @Query("SELECT COUNT(*) FROM countries WHERE unlocked = 1")
    fun getUnlockedCountriesCount(): Flow<Int>
} 