package com.example.serviceexample

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.util.*


class RandomService : Service() {

    lateinit var job: Job
    private var numero = MutableLiveData<Int>()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "Servicio arrancado", Toast.LENGTH_SHORT).show()

        numero.observeForever({
            Toast.makeText(
                applicationContext,
                "Numero: " + numero.value.toString(),
                Toast.LENGTH_SHORT).show()
        })

        job = MainScope().launch {
            showNumber()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        runBlocking {
            job.cancel()
        }
        super.onDestroy()
    }

    suspend private fun showNumber() = withContext(Dispatchers.IO){
        try {
            while (isActive) {
                val random = Random()
                numero.postValue(random.nextInt())
                Thread.sleep(3000)
            }
        }
        catch (e:CancellationException){

        }
        finally {

        }
    }
}
