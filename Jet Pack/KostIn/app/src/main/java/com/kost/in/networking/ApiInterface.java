package com.kost.in.networking;

import com.kost.in.data.response.ModelResultDetail;
import com.kost.in.data.response.ModelResultNearby;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("nearbysearch/json")
    Call<ModelResultNearby> getDataResult(@Query("key") String key,
                                          @Query("keyword") String keyword,
                                          @Query("location") String location,
                                          @Query("rankby") String rankby);
    @GET("details/json")
    Call<ModelResultDetail> getDetailResult(@Query("key") String key,
                                          @Query("placeid") String placeid);
}
