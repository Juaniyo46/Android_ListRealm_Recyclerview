package com.example.ejerciciolistas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button nextBtn;
    Button loadBtn;
    RecyclerView rv;
    RVAdapter adapter;
    List<Sitio> listado;

    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Abrimos la base de datos 'DBSitios' en modo escritura
        SitiosSQLiteHelper sitiodbh =
                new SitiosSQLiteHelper(this, "Sitios", null, 1);
        db = sitiodbh.getWritableDatabase();


        listado = new ArrayList<>();

        //RecyclerView
        rv = findViewById(R.id.lista);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        adapter = new RVAdapter(listado);
        rv.setAdapter(adapter);

        nextBtn = findViewById(R.id.siguiente);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SaveActivity.class );
                startActivityForResult(intent, 2);
            }

        });

        loadBtn = findViewById(R.id.showData);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // tengo que recuperar datos de bbdd y pasarlos al arraylist de sitios

                String[] campos = new String[] {"nombre","descripcion"};
                Cursor c = db.query("Sitios", campos,null,null,null,null,null);

                //Recorremos los resultados para mostrarlos en pantalla.
                if (c.moveToFirst()){
                    Log.e("testSQLITE", "hay datos");
                    do {
                        String nom = c.getString(0);
                        String des = c.getString(1);

                        listado.add(new Sitio(nom,  des));
                    } while (c.moveToNext());
                } else {
                    Log.e("testSQLITE", "no hay datos");
                }
                adapter.notifyDataSetChanged();
            }

        });


    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            String name = data.getStringExtra("sitio");
            String desc = data.getStringExtra("descripcion");

            //Alternativa 2: método insert()
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre", name);
            nuevoRegistro.put("descripcion",desc);
            db.insert("Sitios", null, nuevoRegistro);

            listado.add(new Sitio(name, desc));
            adapter.notifyDataSetChanged();
        }
    }
}
