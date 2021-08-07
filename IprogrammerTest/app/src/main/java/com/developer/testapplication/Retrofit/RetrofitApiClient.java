package com.developer.testapplication.Retrofit;


import com.developer.testapplication.Model.PhotoListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiClient {
    @GET("photos")
    Call<ArrayList<PhotoListModel>> getPhotoList();
}
