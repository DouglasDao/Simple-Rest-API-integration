package com.fact.webservice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {  /*  @Multipart
    @POST("updatedProfilePic")
    Call<ResponseBody> callUpdateProfilePic(@Header("Authorization") String token, @Part MultipartBody.Part file);

    @POST("updateProfile")
    Call<ResponseBody> callUserRegister(@Header("Authorization") String token, @Body UserProfile data);*/

    @GET("random/{category}")
    Call<ResponseBody> performRandomFact(@Path("category") String category);

    @GET("{digitOrDate}/{category}")
    Call<ResponseBody> performSpecificFact(@Path("digitOrDate") String digdate, @Path("category") String category);

}