package com.example.baitap3.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {
    String BASE_URL = "http://drive.google.com/" ;

    @GET
    Call<ResponseBody> getHtmlResponse(@Url String downloadString);
}
