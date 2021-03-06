package com.whatsappclone.serverHelpers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

    String jio = "192.168.225.106";
    String hotspot = "192.168.43.198";
    public RetrofitInterface retrofitInterface;
    public String BASE_URL = "http://"+jio+":3000/";

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
