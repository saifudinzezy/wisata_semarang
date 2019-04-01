package com.example.cyber_net.wisatasemarang.helper;

import android.os.BatteryManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cyber_net on 11/03/2018.
 */

public class ServiceGenerator {
    private static final String BASE_URL = "https://script.google.com/macros/s/AKfycbxv8tJOYEZklEiwHVaUjUqih9IOorNweaTAf8gi130fRdseMxs/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static <S> S craeteService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
