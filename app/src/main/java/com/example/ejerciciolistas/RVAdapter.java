package com.example.ejerciciolistas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SitioViewHolder> {


    public static class SitioViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView desc;

        public SitioViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.inserccion);
            desc = view.findViewById(R.id.descripcion);
        }
    }

    List<Sitio> sitios;

    RVAdapter(List<Sitio> sitios){
        this.sitios = sitios;
    }

    @NonNull
    @Override
    public SitioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,
                viewGroup, false);
        final SitioViewHolder sitioViewHolder = new SitioViewHolder(v);



        return sitioViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SitioViewHolder sitioViewHolder, int i) {
        sitioViewHolder.name.setText(sitios.get(i).getName());
        sitioViewHolder.desc.setText(sitios.get(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return sitios.size();
    }
}


