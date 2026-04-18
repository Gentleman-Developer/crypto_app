package com.example.koincrypto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koincrypto.model.CryptoModel
import com.example.koincrypto.service.CryptoAPI
import com.example.koincrypto.view.RecyclerViewAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel : ViewModel() {

    var cryptoList = MutableLiveData<List<CryptoModel>>()
    var cryptoLoading = MutableLiveData<Boolean>()
    var cryptoError = MutableLiveData<Boolean>()



    var job : Job? = null

    val exceptions = CoroutineExceptionHandler { context, throwable ->
        println("Error: ${throwable.message}")
        cryptoLoading.value = false
    }

    fun getDataFromAPI() {
        val BASE_URL = "https://raw.githubusercontent.com/"
        // Build Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        /*
        viewModelScope.launch(Dispatchers.IO + exceptions) {  }

        You can use both, if you need to use "job", use the "job" one
         */

        job = CoroutineScope(Dispatchers.IO + exceptions).launch {

            val response = retrofit.getData()

            withContext(Dispatchers.Main) {

                if(response.isSuccessful){
                    //Loading and Error disappears
                    cryptoLoading.value = false
                    cryptoError.value = false
                    response.body()?.let{
                    cryptoList.value = it
                    }
                }
            }

        }
    }
}