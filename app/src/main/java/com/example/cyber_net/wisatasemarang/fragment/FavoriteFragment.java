package com.example.cyber_net.wisatasemarang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyber_net.wisatasemarang.R;
import com.example.cyber_net.wisatasemarang.adapter.WisataAdapter;
import com.example.cyber_net.wisatasemarang.db.DatabaseHelper;
import com.example.cyber_net.wisatasemarang.model.Wisata;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    RecyclerView rvFavorit;
    DatabaseHelper database;
    ArrayList<Wisata> listWisataFavorite;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvFavorit = view.findViewById(R.id.rv_favorit);

        //setting layout
        rvFavorit.setLayoutManager(new LinearLayoutManager(getActivity()));

        //mencetak db sqlite
        database = new DatabaseHelper(getActivity());

        //mendapatkan data wisata favorite dan dimasukan ke dalam listWisataFavorite
        listWisataFavorite = database.getDataFavorite();

        WisataAdapter adapter = new WisataAdapter(getActivity(), listWisataFavorite);
        rvFavorit.setAdapter(adapter);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
