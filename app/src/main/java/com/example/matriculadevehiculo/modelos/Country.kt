package com.example.matriculadevehiculo.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_country")
data class Country (
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    var pais:String,
    var departamento:String
       )
