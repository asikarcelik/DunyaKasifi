package com.asikarcelik.dnyakaifi.data.repository

import com.asikarcelik.dnyakaifi.data.local.dao.CountryDao
import com.asikarcelik.dnyakaifi.data.model.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Ülke verilerini yöneten repository sınıfı
 */
@Singleton
class CountryRepository @Inject constructor(
    private val countryDao: CountryDao
) {
    /**
     * Tüm ülkeleri akış olarak getirir
     */
    fun getAllCountries(): Flow<List<Country>> = countryDao.getAllCountries()
    
    /**
     * Ülke koduna göre bir ülkeyi akış olarak getirir
     */
    fun getCountryByCode(countryCode: String): Flow<Country> = countryDao.getCountryByCode(countryCode)
    
    /**
     * Kıtaya göre ülkeleri akış olarak getirir
     */
    fun getCountriesByContinent(continent: String): Flow<List<Country>> = 
        countryDao.getCountriesByContinent(continent)
    
    /**
     * Açık olan (kilidini açılmış) ülkeleri akış olarak getirir
     */
    fun getUnlockedCountries(): Flow<List<Country>> = countryDao.getUnlockedCountries()
    
    /**
     * Açık olan ülke sayısını akış olarak getirir
     */
    fun getUnlockedCountriesCount(): Flow<Int> = countryDao.getUnlockedCountriesCount()
    
    /**
     * Birden fazla ülkeyi veritabanına ekler
     */
    suspend fun insertCountries(countries: List<Country>) {
        countryDao.insertCountries(countries)
    }
    
    /**
     * Tek bir ülkeyi veritabanına ekler
     */
    suspend fun insertCountry(country: Country) {
        countryDao.insertCountry(country)
    }
    
    /**
     * Bir ülkeyi günceller
     */
    suspend fun updateCountry(country: Country) {
        countryDao.updateCountry(country)
    }
    
    /**
     * Bir ülkenin kilit durumunu günceller
     */
    suspend fun updateUnlockStatus(countryCode: String, isUnlocked: Boolean) {
        countryDao.updateUnlockStatus(countryCode, isUnlocked)
    }
} 