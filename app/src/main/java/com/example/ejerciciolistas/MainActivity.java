package com.example.ejerciciolistas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    Button nextBtn;
    Button loadBtn;
    RecyclerView rv;
    RVAdapter adapter;
    RealmResults<Sitio> listado;





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
        adapter = new RVAdapter(getApplicationContext(),listado);
        rv.setAdapter(adapter);

        listenOnclick();
    }

    private void listenOnclick(){
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sitio sitio = listado.get(rv.getChildAdapterPosition(v));
                alertEditarSitio(sitio);
            }
        });

        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SitioRepository.eliminarSitio(v,realm,rv,listado);
                return false;
            }
        });
    }

    public void alertEditarSitio(final Sitio sitio){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogo,null);
        builder.setView(view);

        final EditText etSitio = view.findViewById(R.id.nombreSitio);
        final EditText etDescripcion = view.findViewById(R.id.descripcionSitio);
        builder.setMessage("Editar");

        etSitio.setText(sitio.getName());
        etDescripcion.setText(sitio.getDescripcion());

        etSitio.setSelection(etSitio.getText().length()); //Posicionamos el cursor al final

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String nombreSitio = etSitio.getText().toString();
                String descripcion = etDescripcion.getText().toString();
                if (nombreSitio.length()>0){
                    realm.beginTransaction();
                    sitio.setName(nombreSitio);
                    sitio.setDescripcion(descripcion);
                    realm.commitTransaction();
                } else
                    Toast.makeText(getApplicationContext(), "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
    public void onChange(RealmResults<Sitio> sitios) {
        adapter.notifyDataSetChanged();
    }
}
