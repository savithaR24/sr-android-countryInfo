package com.kodeco.android.countryinfo

import android.app.Application
import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://restcountries.com/v3.1/"

class CountryInfoApp : Application() {

    lateinit var repository: CountryRepository
    override fun onCreate() {
        super.onCreate()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val apiService: CountryAPIService = retrofit.create(CountryAPIService::class.java)

        repository = CountryRepositoryImpl(apiService)

    }

}