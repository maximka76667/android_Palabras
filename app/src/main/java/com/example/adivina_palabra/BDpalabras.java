package com.example.adivina_palabra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Scanner;

public class BDpalabras extends SQLiteOpenHelper {

    final static String DB_NAME = "palabras";
    final static int VERSION = 2;
    Context contexto;
    SQLiteDatabase db = null;

    public BDpalabras(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.contexto = context;
    }

    public void abreBD() {
        if (db == null) {
            db = this.getReadableDatabase();
        }
    }

    public void cierraBD() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String todasLasSetencias = Utilidades.obtentTextoFicheroCarpetaAssets(contexto.getAssets().open("palabras5c.sql"));

            Scanner scanner = new Scanner(todasLasSetencias);

            scanner.useDelimiter(";");

            while (scanner.hasNext()) {
                String sentence = scanner.next();
                sqLiteDatabase.execSQL(sentence);
            }

            onUpgrade(sqLiteDatabase, 1, VERSION);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public String obtenPalabraAleatoria() {
        abreBD();

        String SQLSelect = "SELECT * FROM tbpalabras ORDER BY RANDOM() LIMIT 1";
        Cursor rows = db.rawQuery(SQLSelect, null);

        rows.moveToFirst();

        int palabraIndex = rows.getColumnIndex("palabra");
        String palabraAleatoria = rows.getString(palabraIndex);

        rows.close();

        return palabraAleatoria.toUpperCase();
    }

    public boolean palabraEnDiccionario(String palabraBuscar) {
        abreBD();
        Cursor rows = db.rawQuery("SELECT * FROM tbpalabras WHERE palabra = ?", new String[]{palabraBuscar.toLowerCase()});
        return rows.moveToFirst();
    }


}
