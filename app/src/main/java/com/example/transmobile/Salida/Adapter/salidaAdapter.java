package com.example.transmobile.Salida.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmobile.R;
import com.example.transmobile.Salida.Modelos.Salida;

import java.util.List;

public class salidaAdapter extends RecyclerView.Adapter<salidaAdapter.MyViewHolder>  {

    private Context context;
    private List<Salida> salidaList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_id;
        public TextView tv_motor;
        public TextView tv_num_certificado;
        public TextView tv_num_cliente;
        public TextView tv_chasis;
        public TextView tv_descripcion;
        public TextView tv_color;
        public TextView tv_id_patio;
        public TextView tv_axco;
        public TextView tv_modelo;
        public TextView tv_num_liquidacion;
        public TextView tv_num_order_entrada;
        public TextView tv_transmitador;

        public RelativeLayout rl_main;

        // public ImageButton im_deleteReference;

        public MyViewHolder(View view) {
            super(view);

            //tv_id = view.findViewById(R.id);
            //tv_num_certificado = view.findViewById(R.id);
            tv_motor = view.findViewById(R.id.tvMotor);
            tv_num_cliente = view.findViewById(R.id.tvCliente);
            tv_chasis = view.findViewById(R.id.tvChasis);
            tv_descripcion = view.findViewById(R.id.tvDescripcion);
            tv_color = view.findViewById(R.id.tvColor);
            rl_main = view.findViewById(R.id.contenedorObtenerAuto);
            //tv_id_patio = view.findViewById(R.id.);
            //tv_axco = view.findViewById(R.id.);
            //tv_modelo = view.findViewById(R.id.);
            //tv_num_liquidacion = view.findViewById(R.id.);
            //tv_num_order_entrada = view.findViewById(R.id.);
            //tv_transmitador = view.findViewById(R.id.);

        }
    }

    public salidaAdapter(Context context, List<Salida> salidaList) {
        this.context = context;
        this.salidaList = salidaList;
    }

    @NonNull
    @Override
    public salidaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_auto_layout, parent, false);

        return new salidaAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull salidaAdapter.MyViewHolder holder, final int position) {

        holder.tv_motor.setText(salidaList.get(position).getMotor());
        holder.tv_num_cliente.setText(salidaList.get(position).getNombre_cliente());
        holder.tv_chasis.setText(salidaList.get(position).getChasis());
        holder.tv_descripcion.setText(salidaList.get(position).getDescripcion());
        holder.tv_color.setText(salidaList.get(position).getColor());

     /*   holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertbox = new AlertDialog.Builder(view.getContext())
                        .setTitle("¿ESTÁ SEGURO?")
                        .setMessage("¿SEGURO DESEA REALIZAR LA SALIDA DEL CHASIS\n\n "+ salidaList.get(position).getChasis() + "?"+  salidaList.get(position).getId())
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                               /* startActivity(new Intent(V_MainActivity.this, V_Login.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();

                              // view.

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        })
                        .show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return salidaList.size();
    }

}
