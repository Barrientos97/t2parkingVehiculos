package com.example.matriculadevehiculo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matriculadevehiculo.R
import com.example.matriculadevehiculo.databinding.ActivityMainBinding
import com.example.matriculadevehiculo.fragment.ViewVehicleFragment


class MainActivity () : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewPlateFragment()

    }

    private fun viewPlateFragment(){

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container_view,
                ViewVehicleFragment.newInstance(),
                "siguiente"
            )
            .commit()

    }

}