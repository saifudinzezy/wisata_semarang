package com.example.cyber_net.wisatasemarang.helper;

import com.example.cyber_net.wisatasemarang.model.ListWisata;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cyber_net on 11/03/2018.
 */

public interface ServiceClient {
    @GET("exec")
    Call<ListWisata> getWisata(@Query("sheet") String namaSheet);
}
