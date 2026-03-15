package com.sameershelar.bmicalculator.di

import com.sameershelar.bmicalculator.data.DataStoreRepository
import com.sameershelar.bmicalculator.ui.viewmodels.HeightInputScreenViewModel
import com.sameershelar.bmicalculator.ui.viewmodels.HomeScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DataStoreRepository(androidContext()) }
    viewModel { HeightInputScreenViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }
}
