package com.example.ejerciciolistas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity  implements RealmChangeListener<RealmResults<Sitio>> {

    public Realm realm;

    private Button nextBtn;
    private RecyclerView rv;
    private RVAdapter adapter;
    private RealmResults<Sitio> listado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        findViewID();

        nextBtn = findViewById(R.id.siguiente);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SaveActivity.class);
                startActivityForResult(intent,2);
            }
        });


    }

    private void findViewID(){
        listado = realm.where(Sitio.class).findAll();
        listado.addChangeListener(this);
        rv = findViewById(R.id.lista);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter =new RVAdapter(getApplicationContext(), listado, new ButtonsInterface() {
            @Override
            public void onClick(View view, int i) {
                final Sitio sitio = listado.get(i);
            }
        });

        adapter = new RVAdapter(getApplicationContext(), listado, new ButtonsInterface() {
            @Override
            public void onClick(View view, int i) {
                if (view.getId() == R.id.borrar){
                    final Sitio sitio = listado.get(i);
                    SitioRepository.eliminarSitio(realm,sitio,listado);

                } else if (view.getId() == R.id.editar){
                    final Sitio sitio = listado.get(i);
                    SitioRepository.alertEditarSitio(sitio,MainActivity.this,realm);
                }


            }
        });
        rv.setAdapter(adapter);


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sitio sitio = listado.get(rv.getChildAdapterPosition(v));
                SitioRepository.alertEditarSitio(sitio, MainActivity.this, realm );
            }
        });
        listenOnclick();
    }

    private void listenOnclick(){



        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Sitio sitio = listado.get(rv.getChildAdapterPosition(v));
                SitioRepository.eliminarSitio(realm,sitio,listado);
                return false;
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            String name = data.getStringExtra("sitio");
            String desc = data.getStringExtra("descripcion");

            SitioRepository.guardarSitio(realm, name, desc);

        }
    }

    @Override
    public void onChange(@NonNull  RealmResults<Sitio> sitios) {
        adapter.notifyDataSetChanged();
    }
}
