package com.example.adivina_palabra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class BDpalabras extends SQLiteOpenHelper {

    Context contexto;
    SQLiteDatabase db=null;

    final static String DB_NAME = "palabras";
    final static int VERSION = 2;

    public BDpalabras(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.contexto = context;
    }

    public void abreBD() {
        if (db==null) {
            db = this.getReadableDatabase();
        }
    }

    public  void cierraBD() {
        if (db!=null) {
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

            Log.e("321", "Hello");

            while(scanner.hasNext()) {
                String sentence = scanner.next();
                Log.e("SQL", sentence);
                db.execSQL(sentence);
            }

            onUpgrade(db,1,VERSION);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public String obtenPalabraAleatoria(){
        abreBD();
		String palabraAleatoria="";
		
		// Completar
		
        return palabraAleatoria;
    }

    public boolean palabraEnDiccionario(String palabraBuscar) {
        abreBD();
		boolean existe=false;
       
	   // Completar
	   
        return existe;
    }


}
