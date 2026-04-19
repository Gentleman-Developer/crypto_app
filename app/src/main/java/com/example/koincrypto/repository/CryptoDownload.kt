package com.example.koincrypto.repository

import com.example.koincrypto.model.CryptoModel
import com.example.koincrypto.util.Resource

interface CryptoDownload {

    //Download interface
    suspend fun downloadCryptos() : Resource<List<CryptoModel>>
}