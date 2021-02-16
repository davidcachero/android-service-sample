package com.example.serviceexample

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonStart.setOnClickListener {
            if (!servicioArrancado()) {
                arrancarServicio()
            }
        }

        binding.buttonStop.setOnClickListener{
            if (servicioArrancado()) {
                pararServicio()
            }
        }
    }

    private fun arrancarServicio(){
        startService(Intent(applicationContext, RandomService::class.java))
    }

    private fun pararServicio(){
        stopService(Intent(applicationContext, RandomService::class.java))
    }

    private fun servicioArrancado():Boolean{
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)){
            if (service.service.className == RandomService::class.java.name){
                return true
            }
        }
        return false
    }
}