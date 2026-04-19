package com.example.koincrypto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koincrypto.model.CryptoModel
import com.example.koincrypto.repository.CryptoDownload
import com.example.koincrypto.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel(
    private var cryptoDownloadRepository : CryptoDownload
) : ViewModel() {

    var cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    var cryptoLoading = MutableLiveData<Resource<Boolean>>()
    var cryptoError = MutableLiveData<Resource<Boolean>>()



    var job : Job? = null

    val exceptions = CoroutineExceptionHandler { context, throwable ->
        println("Error: ${throwable.message}")
        cryptoLoading.value = Resource.error(throwable.localizedMessage?: "Error 1", data = true)
    }

    fun getDataFromAPI() {

        cryptoLoading.value = Resource.loading(true)


        job = CoroutineScope(Dispatchers.IO + exceptions).launch {
                val resource = cryptoDownloadRepository.downloadCryptos()

            withContext(Dispatchers.Main) {

                resource.data?.let {
                    cryptoList.value = resource
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("",false)

                }
                }
            }

        }
    }