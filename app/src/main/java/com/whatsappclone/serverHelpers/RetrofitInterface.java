package com.whatsappclone.serverHelpers;


import com.whatsappclone.modelClass.Model;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/genOtp")
    Call<Model> generateOTP(@Body HashMap<String, String> map);


}
