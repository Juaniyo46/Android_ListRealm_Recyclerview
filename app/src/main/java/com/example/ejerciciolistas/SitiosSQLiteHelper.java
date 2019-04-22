package com.example.ejerciciolistas;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import android.database.sqlite.SQLiteOpenHelper;

public class SitiosSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL pare crear la tabla
    String sqlCreate = "CREATE TABLE Sitios (nombre TEXT, descripcion TEXT)";

    public SitiosSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Eliminamos la version anterior de la tabla.
        db.execSQL("DROP TABLE IF EXISTS Sitios");

        //Scre crea la nueva version de la tabla.
        db.execSQL(sqlCreate);
    }
}
