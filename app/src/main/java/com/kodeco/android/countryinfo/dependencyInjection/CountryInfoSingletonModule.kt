package com.kodeco.android.countryinfo.dependencyInjection

import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(CountryAPIService::class.java)
    }

    @Provides
    @Singleton
    fun providesCountryRepository(
        service: CountryAPIService
    ): CountryRepository = CountryRepositoryImpl(service)
}