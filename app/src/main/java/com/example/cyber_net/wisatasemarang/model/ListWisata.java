package com.example.cyber_net.wisatasemarang.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Cyber_net on 11/03/2018.
 */

public class ListWisata {
    //TODO tadi errornya karena serialize tidak sama dengan json nya menulisnya S besar yg bnr s kecil
    @SerializedName("semarang")
    private List<Wisata> listWisataSemarang;

    public List<Wisata> getListWisataSemarang() {
        return listWisataSemarang;
    }
}
