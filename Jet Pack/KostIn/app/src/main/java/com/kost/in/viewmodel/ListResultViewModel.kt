package com.kost.`in`.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kost.`in`.data.model.ModelDetail
import com.kost.`in`.data.model.ModelResults
import com.kost.`in`.data.response.ModelResultDetail
import com.kost.`in`.data.response.ModelResultNearby
import com.kost.`in`.networking.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ListResultViewModel : ViewModel() {

    companion object {
        val strApiKey = "AIzaSyCgtlTsEK2YlPf6He9-eZg1o0DXUsfIOgc"
    }

    private val modelResultsMutableLiveData = MutableLiveData<ArrayList<ModelResults>>()
    private val modelDetailMutableLiveData = MutableLiveData<ModelDetail>()

    fun setClinicLocation(strLocation: String) {
        val apiService = ApiClient.getClient()
        val call = apiService.getDataResult(strApiKey, "kost-putri", strLocation, "distance")
        call.enqueue(object : Callback<ModelResultNearby> {
            override fun onResponse(
                call: Call<ModelResultNearby>, response: Response<ModelResultNearby>) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(response.body()?.modelResults)
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }

    fun setHospitalLocation(strLocation: String) {
        val apiService = ApiClient.getClient()
        val call = apiService.getDataResult(strApiKey, "kost-putra", strLocation, "distance")
        call.enqueue(object : Callback<ModelResultNearby> {
            override fun onResponse(
                call: Call<ModelResultNearby>,
                response: Response<ModelResultNearby>
            ) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(response.body()?.modelResults)
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }

    fun setDoctorLocation(strLocation: String) {
        val apiService = ApiClient.getClient()
        val call = apiService.getDataResult(strApiKey, "kontrakan", strLocation, "distance")
        call.enqueue(object : Callback<ModelResultNearby> {
            override fun onResponse(
                call: Call<ModelResultNearby>,
                response: Response<ModelResultNearby>
            ) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(response.body()?.modelResults)
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }
    fun setDrugStoreLocation(strLocation: String) {
        val apiService = ApiClient.getClient()
        val call = apiService.getDataResult(strApiKey, "kost-bebas", strLocation, "distance")
        call.enqueue(object : Callback<ModelResultNearby> {
            override fun onResponse(
                call: Call<ModelResultNearby>,
                response: Response<ModelResultNearby>
            ) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(response.body()!!.modelResults)
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }
    fun setDetailLocation(strPlaceID: String) {
        val apiService = ApiClient.getClient()
        val call = apiService.getDetailResult(strApiKey, strPlaceID)
        call.enqueue(object : Callback<ModelResultDetail> {
            override fun onResponse(
                call: Call<ModelResultDetail>,
                response: Response<ModelResultDetail>
            ) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    (response.body()?.modelDetail)
                    modelDetailMutableLiveData.postValue(response.body()?.modelDetail)
                }
            }

            override fun onFailure(call: Call<ModelResultDetail>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }
    fun getClinicLocation(): LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData

    fun getHospitalLocation(): LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData

    fun getDoctorLocation(): LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData

    fun getDrugStoreLocation(): LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData

    fun getDetailLocation(): LiveData<ModelDetail> = modelDetailMutableLiveData

}

