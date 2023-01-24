package com.example.matriculadevehiculo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface VehiculoDao {
    //Add new plates
    @Insert
    suspend fun insertVehicle(plate: Vehicle)

    //get All Plate
    @Query("select * from Vehicle_table")
    suspend fun getAllVehicle(): List<Vehicle>

    //get Plate
    @Query("select * from Vehicle_table where plate = :plate")
    suspend fun getPlate(plate: String): Vehicle?

    //get predetemined to be true
    @Query("select * from Vehicle_table where predetermined = 1")
    suspend fun getPredetemined(): Vehicle?

    //update Plate
    @Query("UPDATE Vehicle_table set plate=:plate,hashtag=:hashtag,plateId=:plateId," +
            "typeVehicle=:typeVehicle,predetermined=:predetermined  where  id = :id")
    suspend fun updateIdVehicule(plate:String, hashtag:String, plateId:String,
                                 typeVehicle:Int, predetermined:Boolean, id:Int)
    //update predetemined
    @Query("UPDATE Vehicle_table set predetermined=:predetermined = (CASE id  WHEN  :id THEN 1 ELSE 0 END)" +
            " where predetermined = 1 OR  id = :id ")
    suspend fun updatePredetemined(predetermined: Boolean, id: Int)

    // delete Plate
    @Query("DELETE from Vehicle_table where id = :id")
    suspend fun deleteIdVehicle(id: Int)

    @Update
    suspend fun updateVehicle(plate: Vehicle)

}