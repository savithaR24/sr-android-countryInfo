package com.kodeco.android.countryinfo.api

import com.kodeco.android.countryinfo.models.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryAPIService {
    @GET("all")
    suspend fun getAllCountries(): Response<List<Country>>
}