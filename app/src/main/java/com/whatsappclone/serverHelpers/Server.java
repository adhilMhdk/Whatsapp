package com.whatsappclone.serverHelpers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

    public RetrofitInterface retrofitInterface;
    public String BASE_URL = "http://ec2-18-221-31-144.us-east-2.compute.amazonaws.com:3000";

    public Server() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }
}
