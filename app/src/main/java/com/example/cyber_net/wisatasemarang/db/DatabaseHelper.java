package com.example.cyber_net.wisatasemarang.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cyber_net.wisatasemarang.model.Wisata;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "dbwisata";
    private final static String DATABASE_TABLE = "table_wisata";
    private final static String WISATA_ID = "_id";
    private final static String NAMA_WISATA = "nama_wisata";
    private final static String GAMBAR_WISATA = "gambar_wisata";
    private final static String ALAMAT_WISATA = "alamat_wisata";
    private final static String DESKRIPSI_WISATA = "deskripsi_wisata";
    private final static String LATITUDE_WISATA = "latitude_wisata";
    private final static String LONGITUDE_WISATA = "longitude_wisata";

    private final static int DATABASE_VERSION = 1;

    private final static String CREATE_TABLE = "CREATE TABLE " +DATABASE_TABLE
            + " (" +WISATA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +NAMA_WISATA+ " VARCHAR(200), "
            +GAMBAR_WISATA+ " VARCHAR(200), "
            +ALAMAT_WISATA+ " TEXT, "
            +DESKRIPSI_WISATA+ " TEXT, "
            +LATITUDE_WISATA+ " VARCHAR(20), "
            +LONGITUDE_WISATA+ " VARCHAR(20));";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    //buat sqlite
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +DATABASE_TABLE);
        onCreate(db);
    }

    //insert db
    public long insertData(String namaWisata, String gambarWisata,String alamatWisata,
                           String deskripsiWisata, String latWisata, String longWisata){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(NAMA_WISATA, namaWisata);
            values.put(GAMBAR_WISATA, gambarWisata);
            values.put(ALAMAT_WISATA, alamatWisata);
            values.put(DESKRIPSI_WISATA, deskripsiWisata);
            values.put(LATITUDE_WISATA, latWisata);
            values.put(LONGITUDE_WISATA, longWisata);
        long id = db.insert(DATABASE_TABLE, null, values);
        db.close();
        return id;
    }
    //mengambil data
    public ArrayList<Wisata> getDataFavorite(){
        ArrayList<Wisata> listWisata = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String[] columnName = {
                WISATA_ID, NAMA_WISATA, GAMBAR_WISATA, ALAMAT_WISATA,
                DESKRIPSI_WISATA, LATITUDE_WISATA, LONGITUDE_WISATA
        };
        Cursor cursor = db.query(DATABASE_TABLE, columnName, null, null, null, null, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                int idWisata = cursor.getInt(cursor.getColumnIndex(WISATA_ID));
                String namaWisata = cursor.getString(cursor.getColumnIndex(NAMA_WISATA));
                String gambarWisata = cursor.getString(cursor.getColumnIndex(GAMBAR_WISATA));
                String alamatWisata = cursor.getString(cursor.getColumnIndex(ALAMAT_WISATA));
                String deskripsiWisata = cursor.getString(cursor.getColumnIndex(DESKRIPSI_WISATA));
                String latWisata = cursor.getString(cursor.getColumnIndex(LATITUDE_WISATA));
                String longWisata = cursor.getString(cursor.getColumnIndex(LONGITUDE_WISATA));

                //membuat object wisata
                Wisata wisataFavorite = new Wisata(String.valueOf(idWisata), namaWisata, gambarWisata,
                        deskripsiWisata, alamatWisata, latWisata, longWisata);

                //memasukan listview
                listWisata.add(wisataFavorite);
            }
        }
        db.close();
        return listWisata;
    }

    //hapus data
    public int delete(String namaWisata){
        SQLiteDatabase db = getWritableDatabase();
        String namaKolomnya = NAMA_WISATA+ " = ?";
        String[] nilaiFieldnya = {namaWisata};

        int count = db.delete(DATABASE_TABLE, namaKolomnya, nilaiFieldnya);
        return count;
    }
}
