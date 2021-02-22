package com.example.transmobile.Salida.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmobile.R;
import com.example.transmobile.Salida_List;

import java.util.List;

public class Buscar_Autos_Adapter extends RecyclerView.Adapter<Buscar_Autos_Adapter.MyViewHolder>{

    private List<Salida_List>mdata;
    private Context mcontext;
    public Buscar_Autos_Adapter(Context mcontext, List<Salida_List> mdata) {

        this.mdata=mdata;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;

        v = LayoutInflater.from(mcontext).inflate(R.layout.row_search_auto_layout,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvChasis.setText(mdata.get(position).getANUMCLI());
        holder.tvChasis.setText(mdata.get(position).getACHASIS());
        holder.tvMotor.setText(mdata.get(position).getANUMMOTOR());
        holder.tvColor.setText(mdata.get(position).getCOLOR());
        holder.tvDescripcion.setText(mdata.get(position).getDESCRIP1());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView tvCliente,tvChasis, tvMotor, tvColor, tvDescripcion;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView2);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvChasis = itemView.findViewById(R.id.tvChasis);
            tvMotor = itemView.findViewById(R.id.tvMotor);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}
