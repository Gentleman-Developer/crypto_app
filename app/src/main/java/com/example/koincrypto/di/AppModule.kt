package com.example.koincrypto.di

import com.example.koincrypto.repository.CryptoDownload
import com.example.koincrypto.repository.CryptoDownloadImpl
import com.example.koincrypto.service.CryptoAPI
import com.example.koincrypto.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Singleton scope( everytime inject -> use same instance)
    // API
    single {
        val BASE_URL = "https://raw.githubusercontent.com/"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
    }

    // Repository
    single<CryptoDownload> {
        CryptoDownloadImpl(get())
    }

    // ViewModel inject scope
    viewModel {
        CryptoViewModel(get())
    }

    /*Factory scope(everytime inject -> create new instance)
    factory {

    }
     */
}