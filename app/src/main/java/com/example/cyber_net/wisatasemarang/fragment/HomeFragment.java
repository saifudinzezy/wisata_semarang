package com.example.cyber_net.wisatasemarang.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cyber_net.wisatasemarang.R;
import com.example.cyber_net.wisatasemarang.adapter.WisataAdapter;
import com.example.cyber_net.wisatasemarang.helper.ServiceClient;
import com.example.cyber_net.wisatasemarang.helper.ServiceGenerator;
import com.example.cyber_net.wisatasemarang.model.ListWisata;
import com.example.cyber_net.wisatasemarang.model.Wisata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //deklarasi
    RecyclerView rvHome;
    List<Wisata> listWisata = new ArrayList<>();
    ProgressDialog pd;
    SwipeRefreshLayout refreshLayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvHome = view.findViewById(R.id.rv_home);
        refreshLayout = view.findViewById(R.id.refresh);
        //membuat tampilan recycler view menjadi listview
        LinearLayoutManager ll = new LinearLayoutManager(getActivity());
        // dengan methode .setLayoutManager
        rvHome.setLayoutManager(ll);

        //menset properti warna saat merefresh
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        //mensetting listener yang akan dijalankan saat layar refresh
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //handler untuk menjalankan jeda 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //berhenti berputar
                        refreshLayout.setRefreshing(false);

                        //fungsi2 lain yang dijalankan saat refresh berhenti
                        loadData(false);

                    }
                }, 5000); //5 detik
            }
        });

        //load data di background
        loadData(true);

        return view;
    }

    private void loadData(boolean isLoading){
        //membuat object service
        ServiceClient service = ServiceGenerator.craeteService(ServiceClient.class);
        //memilih jenis service yang dibutuhkan
        Call<ListWisata> getListWisata = service.getWisata("semarang");

        //efek loading mengambil data
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        if (isLoading) pd.show();

        //mengirim request dan menerima respon dari server
        getListWisata.enqueue(new Callback<ListWisata>() {
            @Override
            public void onResponse(Call<ListWisata> call, Response<ListWisata> response) {
                //menghilngkan efek loading
                pd.dismiss();
                //menyimpan respon server ke listwisata
                listWisata = response.body().getListWisataSemarang();
                //memasukan listwisata ke dalam adapter
                WisataAdapter adapter = new WisataAdapter(getActivity(), listWisata);
                //setting adapter di rvHome sesuai adapter yg terbentuk
                rvHome.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<ListWisata> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
