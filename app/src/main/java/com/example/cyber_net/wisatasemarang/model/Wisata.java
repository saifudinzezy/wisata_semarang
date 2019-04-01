package com.example.cyber_net.wisatasemarang.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cyber_net on 11/03/2018.
 */

public class Wisata {
    @SerializedName("id_wisata")
    private String idWisata;
    @SerializedName("nama_wisata")
    private String namaWisata;
    @SerializedName("gambar_wisata")
    private String gambarWisata;
    @SerializedName("deskripsi_wisata")
    private String deskripsiWisata;
    @SerializedName("alamat_wisata")
    private String alamatWisata;
    @SerializedName("latitude_wisata")
    private String latitudeWisata;
    @SerializedName("longitude_wisata")
    private String longitudeWisata;

    public Wisata() {
    }

    public Wisata(String idWisata, String namaWisata, String gambarWisata, String deskripsiWisata,
                  String alamatWisata, String latitudeWisata, String longitudeWisata) {
        this.idWisata = idWisata;
        this.namaWisata = namaWisata;
        this.gambarWisata = gambarWisata;
        this.deskripsiWisata = deskripsiWisata;
        this.alamatWisata = alamatWisata;
        this.latitudeWisata = latitudeWisata;
        this.longitudeWisata = longitudeWisata;
    }

    public String getIdWisata() {
        return idWisata;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public String getGambarWisata() {
        return gambarWisata;
    }

    public String getDeskripsiWisata() {
        return deskripsiWisata;
    }

    public String getAlamatWisata() {
        return alamatWisata;
    }

    public String getLatitudeWisata() {
        return latitudeWisata;
    }

    public String getLongitudeWisata() {
        return longitudeWisata;
    }
}
