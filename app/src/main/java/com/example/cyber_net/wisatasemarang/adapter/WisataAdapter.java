package com.example.cyber_net.wisatasemarang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cyber_net.wisatasemarang.activity.DetailWisataActivity;
import com.example.cyber_net.wisatasemarang.R;

import com.example.cyber_net.wisatasemarang.helper.Constans;
import com.example.cyber_net.wisatasemarang.model.Wisata;

import java.util.List;

/**
 * Created by Cyber_net on 11/03/2018.
 */

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.WisataViewHolder>{
    private Context context;
    private List<Wisata> listWisata;

    public WisataAdapter(Context context, List<Wisata> listWisata) {
        this.context = context;
        this.listWisata = listWisata;
    }

    @Override
    public WisataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wisata, parent, false);
        return new WisataViewHolder(view);
    }

    public class WisataViewHolder extends RecyclerView.ViewHolder{
        ImageView ivItemGambarWisata;
        TextView tvItemNamaWisata;
        TextView tvItemAlamatWisata;

        public WisataViewHolder(View itemView) {
            super(itemView);
            ivItemGambarWisata = itemView.findViewById(R.id.iv_item_gambar);
            tvItemNamaWisata = itemView.findViewById(R.id.tv_item_nama);
            tvItemAlamatWisata = itemView.findViewById(R.id.tv_item_alamat);
        }
    }

    @Override
    public void onBindViewHolder(WisataViewHolder holder, final int position) {
        String linkGambar = listWisata.get(position).getGambarWisata();
        Glide.with(context)
                .load("https://drive.google.com/thumbnail?id=" + linkGambar)
                .into(holder.ivItemGambarWisata);
        holder.tvItemNamaWisata.setText(listWisata.get(position).getNamaWisata());
        holder.tvItemAlamatWisata.setText(listWisata.get(position).getAlamatWisata());

        //membuat click pada tiap2 tampilan
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objeck bundel
                Bundle bundle = new Bundle();
                bundle.putString(Constans.NAMA_WISATA, listWisata.get(position).getNamaWisata());
                bundle.putString(Constans.GAMBAR_WISATA, listWisata.get(position).getGambarWisata());
                bundle.putString(Constans.ALAMAT_WISATA, listWisata.get(position).getAlamatWisata());
                bundle.putString(Constans.DESKRIPSI_WISATA, listWisata.get(position).getDeskripsiWisata());

                //mencetak intent
                Intent intent = new Intent(context, DetailWisataActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listWisata.size();
    }
}
