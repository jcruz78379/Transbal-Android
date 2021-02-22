package com.example.transmobile.Inventario.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmobile.Inventario.Modelo.mItemInventario;
import com.example.transmobile.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterInventario extends RecyclerView.Adapter<AdapterInventario.HolderAriculos>{

    private Context context;
    private List<mItemInventario> mItemInventarioList = new ArrayList<mItemInventario>();

    public AdapterInventario(Context context, List<mItemInventario> mArticuloList){
        this.context = context;
        this.mItemInventarioList = mArticuloList;
    }

    @NonNull
    @Override
    public AdapterInventario.HolderAriculos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterInventario.HolderAriculos(LayoutInflater.from(context).inflate(R.layout.item_rv_inventario, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAriculos holder, int position) {
        holder.tv_descripcion.setText(mItemInventarioList.get(position).getDescripcion());
        holder.tvChasis.setText(mItemInventarioList.get(position).getChasis());
        holder.tv_horaRegistro.setText(mItemInventarioList.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return mItemInventarioList.size();
    }

    public class HolderAriculos extends RecyclerView.ViewHolder {
        TextView tv_descripcion, tvChasis, tv_horaRegistro;
        public HolderAriculos(@NonNull View itemView) {
            super(itemView);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
            tvChasis = itemView.findViewById(R.id.tvChasis);
            tv_horaRegistro = itemView.findViewById(R.id.tv_horaRegistro);

        }
    }
}
