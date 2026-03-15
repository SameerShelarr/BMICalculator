package com.sameershelar.bmicalculator

import android.app.Application
import com.sameershelar.bmicalculator.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BMICalculatorApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BMICalculatorApplication)
            modules(appModule)
        }
    }
}
