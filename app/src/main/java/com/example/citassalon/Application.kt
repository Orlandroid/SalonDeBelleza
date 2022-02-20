package com.example.citassalon

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.citassalon.data.preferences.LoginPeferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlin.math.log

@HiltAndroidApp
class Application : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    
}