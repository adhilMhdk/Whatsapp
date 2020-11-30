package com.whatsappclone.serverHelpers;


import com.whatsappclone.modelClass.Model;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

    @POST("/genOtp")
    Call<Model> generateOTP(@Body HashMap<String, String> map);

    @POST("/user/new")
    Call<Void> newUser(@Body HashMap<String, String> map);

    @Multipart
    @POST("/profileImg")
    Call<ResponseBody> setImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

    @POST("/user/name/set")
    Call<Void> setName(@Body HashMap<String, String> map);

    @POST("/user/name/check")
    Call<Void> checkUser(@Body HashMap<String, String> map);


}
