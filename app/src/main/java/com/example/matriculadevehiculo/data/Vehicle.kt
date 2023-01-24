package com.example.matriculadevehiculo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Vehicle_table", indices = [Index(value = ["plate"], unique = true)])
data class Vehicle(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "plate")
        val plate: String,
        val hashtag: String = "",
        val plateId:String = "",
        val typeVehicle: Int,
        var predetermined: Boolean
)
