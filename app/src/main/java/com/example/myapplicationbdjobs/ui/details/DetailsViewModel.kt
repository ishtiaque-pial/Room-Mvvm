package com.example.myapplicationbdjobs.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationbdjobs.api.models.app_model.AppTable
import com.example.myapplicationbdjobs.api.models.details.DetailsResponse
import com.example.myapplicationbdjobs.repository.AppRepository
import com.google.gson.Gson
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _detailsMovieLiveData = MutableLiveData<DetailsResponse?>()
    val detailsMovieLiveData: LiveData<DetailsResponse?>
    get()= _detailsMovieLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun callDetailsMovie(id: Int){

        viewModelScope.launch {
            val response= appRepository.getDetailsMovie(id)

            when(response){
                is NetworkResponse.Success -> {
                    _detailsMovieLiveData.value= response.body
                }
                is NetworkResponse.ServerError -> {
                    _errorLiveData.value= response.body?.statusMessage ?:("something went wrong")
                }
                is NetworkResponse.NetworkError -> {
                    _errorLiveData.value="No internet"
                }
                is NetworkResponse.UnknownError -> {
                    _errorLiveData.value="something went wrong"
                }
            }
        }
    }

    fun addBookmarks(appTable: AppTable){
        viewModelScope.launch {
            appRepository.addBookmark(appTable)
        }
    }

    fun getDbdata(){
        viewModelScope.launch {
            val res = appRepository.getDbData()
            Log.e("sdhgfsdjh","now"+Gson().toJson(res))
        }
    }

    fun check(id:Int){
        viewModelScope.launch {
            val res = appRepository.check(id)
            Log.e("sdhgfsdjh","now"+res)
        }
    }



}