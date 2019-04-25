package com.example.ejerciciolistas;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmResults;

public class SitioRepository {


    public static void guardarSitio(Realm realm, String nombre, String descripcion){
        realm.beginTransaction();
        Sitio sitio = new Sitio(nombre, descripcion);
        realm.copyToRealm(sitio);
        realm.commitTransaction();
    }

    public static void eliminarSitio (Realm realm, Sitio sitio, RealmResults<Sitio> listado){

        realm.beginTransaction();
        assert sitio != null;
        sitio.deleteFromRealm();
        realm.commitTransaction();
    }


}
