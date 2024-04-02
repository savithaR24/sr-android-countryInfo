package com.kodeco.android.countryinfo

import android.app.Application
import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@HiltAndroidApp
class CountryInfoApp : Application() {
}