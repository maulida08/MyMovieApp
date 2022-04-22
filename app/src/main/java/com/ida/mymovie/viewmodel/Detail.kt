package com.ida.mymovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ida.mymovie.model.GetDetailMovie
import com.ida.mymovie.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detail: ViewModel() {
    val dataDetail : MutableLiveData<GetDetailMovie> = MutableLiveData()
    fun getDetail(id : Int){
        ApiClient.instance.getALLDetail(id)
            .enqueue(object : Callback<GetDetailMovie> {

                override fun onResponse(
                    call: Call<GetDetailMovie>,
                    response: Response<GetDetailMovie>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        dataDetail.postValue(body)
                    }
                }
                override fun onFailure(call: Call<GetDetailMovie>, t: Throwable) {
                }
            })
    }
}