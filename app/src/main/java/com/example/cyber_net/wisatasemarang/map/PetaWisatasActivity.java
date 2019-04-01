package com.example.cyber_net.wisatasemarang.map;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cyber_net.wisatasemarang.R;
import com.example.cyber_net.wisatasemarang.adapter.WisataAdapter;
import com.example.cyber_net.wisatasemarang.helper.ServiceClient;
import com.example.cyber_net.wisatasemarang.helper.ServiceGenerator;
import com.example.cyber_net.wisatasemarang.model.ListWisata;
import com.example.cyber_net.wisatasemarang.model.Wisata;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetaWisatasActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ProgressDialog pd;
    List<Wisata> listWisata = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta_wisatas);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //efek loading mengambil data
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        //membuat object service
        ServiceClient service = ServiceGenerator.craeteService(ServiceClient.class);
        //memilih jenis service yang dibutuhkan
        Call<ListWisata> getListWisata = service.getWisata("semarang");

        //mengirim request dan menerima respon dari server
        getListWisata.enqueue(new Callback<ListWisata>() {
            @Override
            public void onResponse(Call<ListWisata> call, Response<ListWisata> response) {
                //menghilngkan efek loading
                pd.dismiss();
                //menyimpan respon server ke listwisata
                listWisata = response.body().getListWisataSemarang();
                //memasukan listwisata ke dalam marker
                for (int i = 0; i < listWisata.size(); i++) {
                    //ambil data longitude dan latitude
                    double latWisata = Double.valueOf(listWisata.get(i).getLatitudeWisata());
                    double longWisata = Double.valueOf(listWisata.get(i).getLongitudeWisata());

                    LatLng lokasiWisata = new LatLng(latWisata, longWisata);
                    mMap.addMarker(new MarkerOptions().position(lokasiWisata).title(listWisata.get(i).getNamaWisata()));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasiWisata, 13));
                }

            }

            @Override
            public void onFailure(retrofit2.Call<ListWisata> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(PetaWisatasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
