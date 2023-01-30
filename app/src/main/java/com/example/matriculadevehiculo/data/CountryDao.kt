package com.example.matriculadevehiculo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matriculadevehiculo.modelos.Country

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun getList(Show:List<Country>)

    @Query("select * from table_country")
    fun listCountry():List<Country>
}