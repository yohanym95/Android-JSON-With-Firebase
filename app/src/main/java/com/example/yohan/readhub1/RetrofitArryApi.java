package com.example.yohan.readhub1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitArryApi {



    @GET("posts?per_page=15")
    Call<List<WPPost>> getPostInfo();



}
