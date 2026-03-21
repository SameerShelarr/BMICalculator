package com.sameershelar.bmicalculator.di

import androidx.room.Room
import com.sameershelar.bmicalculator.data.AppDatabase
import com.sameershelar.bmicalculator.data.BmiRepository
import com.sameershelar.bmicalculator.data.DataStoreRepository
import com.sameershelar.bmicalculator.ui.viewmodels.HeightInputScreenViewModel
import com.sameershelar.bmicalculator.ui.viewmodels.HomeScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule =
    module {
        single { DataStoreRepository(androidContext()) }

        // Room
        single {
            Room
                .databaseBuilder(
                    androidContext(),
                    AppDatabase::class.java,
                    "bmi_calculator_db",
                ).build()
        }
        single { get<AppDatabase>().bmiDao() }
        single { BmiRepository(get()) }

        viewModel { HeightInputScreenViewModel(get()) }
        viewModel { HomeScreenViewModel(get(), get()) }
    }
