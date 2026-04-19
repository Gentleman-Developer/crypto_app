package com.example.koincrypto.repository

import com.example.koincrypto.model.CryptoModel
import com.example.koincrypto.service.CryptoAPI
import com.example.koincrypto.util.Resource

class CryptoDownloadImpl(private val api: CryptoAPI) : CryptoDownload {

    //Download from api as a resource with a list of "CryptoModel"
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {


        return try {
            val response = api.getData()

            //Checking data null or not
            if (response.isSuccessful){

                //If successful add the data
                response.body()?.let {
                    return@let Resource.success(it)

                    //If not successful return Error message with Elvis operator
                } ?: Resource.error("Error 1",null)
            } else {
                //If not successful return Error message
                Resource.error("Error 2",null)
            }
            //If not successful return Error message
        } catch (e: Exception) {
            Resource.error("Error 3",null)
        }
    }
}