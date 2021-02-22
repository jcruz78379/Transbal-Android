package com.example.transmobile.Reimpresion.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmobile.R;
import com.example.transmobile.Reimpresion.Modelos.mReimpresion;


import java.util.ArrayList;
import java.util.List;

public class AdapterReimpresion  extends RecyclerView.Adapter<AdapterReimpresion.HolderReimpresion> {


    private Context context;

    private List<mReimpresion> List = new ArrayList<mReimpresion>();

    public AdapterReimpresion(Context context, java.util.List<mReimpresion> list) {
        this.context = context;
        List = list;
    }

    @NonNull
    @Override
    public HolderReimpresion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          return new AdapterReimpresion.HolderReimpresion(LayoutInflater.from(context).inflate(R.layout.item_rv_reimpresion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderReimpresion holder, int position) {
        holder.textView_cliente.setText(List.get(position).getCliente());
        holder.textView_chasis.setText(List.get(position).getChasis());
        holder.textView_placa.setText("Placa: "+List.get(position).getPlaca());
        holder.textView_patio.setText("Patio: "+List.get(position).getPatio());
        holder.textView_usado.setText("Usado: "+List.get(position).getUsado());

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class HolderReimpresion extends RecyclerView.ViewHolder {
        private TextView textView_cliente, textView_chasis, textView_patio, textView_usado, textView_placa;
        public HolderReimpresion(@NonNull View itemView) {
            super(itemView);
            textView_cliente = itemView.findViewById(R.id.textView_cliente);
            textView_chasis = itemView.findViewById(R.id.textView_chasis);
            textView_patio = itemView.findViewById(R.id.textView_patio);
            textView_usado = itemView.findViewById(R.id.textView_usado);
            textView_placa = itemView.findViewById(R.id.textView_placa);
        }
    }
}


