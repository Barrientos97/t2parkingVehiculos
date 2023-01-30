package com.example.matriculadevehiculo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matriculadevehiculo.adapter.ZoneAdapter
import com.example.matriculadevehiculo.data.AppDatabase
import com.example.matriculadevehiculo.databinding.ActivityZoneBinding
import com.example.matriculadevehiculo.modelos.ContenedorZone
import com.example.matriculadevehiculo.modelos.Country
import com.example.matriculadevehiculo.restService.ApiService
import com.example.matriculadevehiculo.restService.servicios
import com.example.matriculadevehiculo.util.Dialog.dismissProgressDialog
import com.example.matriculadevehiculo.util.Dialog.showProgressDialog
import com.example.matriculadevehiculo.util.Initializer.initProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ZoneActivity : AppCompatActivity() {
    lateinit var binding: ActivityZoneBinding
    
  // private  var count: ArrayList<Country> = arrayListOf()

    private  var ls: ArrayList<String> = arrayListOf()

    lateinit var servicio:ApiService

    lateinit var db: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        servicio = servicios().zone()
        // Init progress dialog
        initProgressDialog(this)

        initRecyclerVie()

        leerRetrofit()

    }


   fun leerRetrofit(){

       // Show progress dialog
       showProgressDialog()

       servicio.getZone().enqueue(object : Callback<ContenedorZone>
        {
            override fun onFailure(call: Call<ContenedorZone>?, t: Throwable?) {
                Toast.makeText(this@ZoneActivity,t?.message,Toast.LENGTH_LONG).show()
                Log.e(t?.message,"onFailure")
            }

            override fun onResponse(
                call: Call<ContenedorZone>,
                response: Response<ContenedorZone>) {

                        ls.clear()

                        if (response.isSuccessful) {
                            for(country in  response.body().country){
                                if (country.pais == "URUGUAY"){
                                    ls.add(country.departamento)

                                    Log.e(country.departamento,"mensajesCountry")
                                    Toast.makeText(this@ZoneActivity,country.departamento,Toast.LENGTH_LONG).show()
                                }
                            }

                            binding.zoneRecycler.adapter?.notifyDataSetChanged()
                            // Dismiss progress dialog
                            dismissProgressDialog()
                        }


                    }

            })

    }
    private fun initRecyclerVie(){
        binding.zoneRecycler.apply {
            layoutManager = LinearLayoutManager(this@ZoneActivity)
            adapter = ZoneAdapter(ls)
        }
    }

    fun getList(country:List<Country>){
        db.getCountryDao().getList(country)
    }

    fun readCountry():List<Country>{
        return  db.getCountryDao().listCountry()
    }

}




