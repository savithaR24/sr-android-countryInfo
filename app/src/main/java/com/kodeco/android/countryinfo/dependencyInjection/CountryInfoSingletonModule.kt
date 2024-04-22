package com.kodeco.android.countryinfo.dependencyInjection

import android.content.Context
import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.database.CountryDatabase
import com.kodeco.android.countryinfo.network.adapter.CountryAdapter
import com.kodeco.android.countryinfo.prefs.CountryPrefs
import com.kodeco.android.countryinfo.prefs.CountryPrefsImpl
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://restcountries.com/v3.1/"

@Module
@InstallIn(SingletonComponent::class)
class CountryInfoSingletonModule {

    @Provides
    @Singleton
    fun providesCountryAPIService(): CountryAPIService {
        val moshi = Moshi.Builder()
            .add(CountryAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(CountryAPIService::class.java)
    }

    @Provides
    @Singleton
    fun providesCountryDatabase(@ApplicationContext applicationContext: Context): CountryDatabase {
        return CountryDatabase.buildDatabase(applicationContext)
    }

    @Provides
    @Singleton
    fun providesCountryPrefs(@ApplicationContext applicationContext: Context): CountryPrefs {
        return CountryPrefsImpl(applicationContext)
    }

    @Provides
    @Singleton
    fun providesCountryRepository(
        service: CountryAPIService,
        database: CountryDatabase,
        prefs: CountryPrefs,
    ): CountryRepository = CountryRepositoryImpl(service, database.countryDao(), prefs)
}