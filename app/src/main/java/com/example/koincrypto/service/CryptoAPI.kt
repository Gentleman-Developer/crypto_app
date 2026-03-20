package com.example.koincrypto.service

import com.example.koincrypto.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    @GET("Gentleman-Developer/crypto_data_test/refs/heads/main/crypto_dataset_test.json")
    suspend fun getData() : Response<List<CryptoModel>>
}


/*
Base Url -> https://raw.githubusercontent.com/
Gentleman-Developer/crypto_data_test/refs/heads/main/crypto_dataset_test.json
*/