package com.example.matriculadevehiculo.modelos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContenedorZone (
    var status:Int,
    var message:String,
   @SerializedName("response") var country: List<Country>
        )

