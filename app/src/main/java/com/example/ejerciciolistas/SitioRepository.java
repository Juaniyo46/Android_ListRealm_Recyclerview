package com.example.ejerciciolistas;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class SitioRepository {



    public static void guardarSitio(Realm realm, String nombre, String descripcion){
        realm.beginTransaction();
        Sitio sitio = new Sitio(nombre, descripcion);
        realm.copyToRealm(sitio);
        realm.commitTransaction();
    }




    public static void eliminarSitio (Realm realm,Sitio sitio, RealmResults<Sitio> listado){
        realm.beginTransaction();
        assert sitio != null;
        sitio.deleteFromRealm();
        realm.commitTransaction();
    }


    public static void alertEditarSitio(final Sitio sitio, final Context context, final Realm realm){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialogo,null);
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
                    Toast.makeText(context, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
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


}
