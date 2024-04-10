package com.kodeco.android.countryinfo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kodeco.android.countryinfo.models.Country

@Database(
    entities = [Country::class],
    version = 1,
    exportSchema = false,
)
abstract class CountryDatabase() : RoomDatabase() {
    abstract fun countryDao() : CountryDao

    companion object {
        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            CountryDatabase::class.java,
            "country_database"
        ).build()
    }
}

