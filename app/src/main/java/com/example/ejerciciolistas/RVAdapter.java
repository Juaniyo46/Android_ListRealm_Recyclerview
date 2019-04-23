package com.example.ejerciciolistas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SitioViewHolder>
        implements View.OnClickListener, View.OnLongClickListener{

    private Context context;
    private List<Sitio> listado;
    private View.OnClickListener listener;
    private View.OnLongClickListener onLongClickListener;




    List<Sitio> sitios;

    RVAdapter(Context applicationContext, List<Sitio> sitios){
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }


    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public static class SitioViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView desc;

        public SitioViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.inserccion);
            desc = view.findViewById(R.id.descripcion);
        }
    }
}


