package com.example.cyber_net.wisatasemarang.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cyber_net.wisatasemarang.R;
import com.example.cyber_net.wisatasemarang.db.DatabaseHelper;
import com.example.cyber_net.wisatasemarang.helper.Constans;

public class DetailWisataActivity extends AppCompatActivity {
    //deklarasi
    ImageView ivGambarWisata;
    TextView tvAlamatWisata, tvDeskripsiWisata;
    String idWisata, namaWisata, gambarWisata, alamatWisata, deskripsiWisata, latWisata, longWisata;
    private static final String TAG = DetailWisataActivity.class.getSimpleName();
    private static final String TAG_PREF = "setting";
    private static final String TAG_FAV = "favorite";
    boolean isFav;
    FloatingActionButton fab;
    DatabaseHelper database = new DatabaseHelper(this);
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //menghubungkan java dengan xml
        ivGambarWisata = findViewById(R.id.iv_detail_gambar);
        tvAlamatWisata = findViewById(R.id.tv_detail_alamat);
        tvDeskripsiWisata = findViewById(R.id.tv_detail_deskripsi);

        //menampung data yang dikirim
        Bundle bundle = getIntent().getExtras();
        final String namaWisata = bundle.getString(Constans.NAMA_WISATA);
        final String alamatWisata = bundle.getString(Constans.ALAMAT_WISATA);
        final String deskripsiWisata = bundle.getString(Constans.DESKRIPSI_WISATA);
        final String gambarWisata = bundle.getString(Constans.GAMBAR_WISATA);

        //memasukan data yang didapatkan ke layout
        getSupportActionBar().setTitle(namaWisata);
        Glide.with(this).load("https://drive.google.com/thumbnail?id=" +gambarWisata).into(ivGambarWisata);
        tvAlamatWisata.setText(alamatWisata);
        tvDeskripsiWisata.setText(deskripsiWisata);

        //cek nilai pref
        preferences = getSharedPreferences(TAG_PREF, MODE_PRIVATE);
        isFav = preferences.getBoolean(TAG_PREF + idWisata, false);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFav){
                    //jika sebelumnya favorite
                    //data favorite akan didelete dari database favorite yang ada di SQLite

                    //menghapus wisata didatabase
                    database.delete(namaWisata);
                    Snackbar.make(view, "Database berhasil dihapus", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    //membuat shared pref
                    //menentukan nama sharef pref
                    SharedPreferences sp = getSharedPreferences(TAG_PREF, MODE_PRIVATE);
                    //membuat objetk editor
                    SharedPreferences.Editor editor = sp.edit();
                    //membuat status fav menjadi false
                    editor.putBoolean(TAG_PREF + idWisata, false);
                    editor.commit();
                    isFav = false;
                }else {
                    //jika sebelumnya not favorite
                    //data akan dimasukan ke dalam database favorite
                    //memasukan wisata favorite ke database favorite
                    long id = database.insertData(namaWisata, gambarWisata, alamatWisata,
                            deskripsiWisata, latWisata, longWisata);

                    if (id <= 0){
                        Snackbar.make(view, "gagal dimasukan", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        Snackbar.make(view, "Database berhasil disimpan", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        SharedPreferences sp = getSharedPreferences(TAG_PREF, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(TAG_PREF + idWisata, true);
                        editor.commit();
                        isFav = true;
                    }
                }
                //memanggil cek favorit
                cekFavorit(isFav);
            }
        });
    }

    private void cekFavorit(boolean isFav){
        if (isFav){
            fab.setImageResource(R.drawable.ic_action_favorit);
        } else {
            fab.setImageResource(R.drawable.ic_action_not_favorit);
        }
    }
}
