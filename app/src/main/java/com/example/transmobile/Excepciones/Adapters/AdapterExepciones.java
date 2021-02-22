package com.example.transmobile.Excepciones.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmobile.Excepciones.FotosActivity;
import com.example.transmobile.Excepciones.Modelos.Excepcion;

import com.example.transmobile.R;
import com.example.transmobile.Global.VarGlobal;

import java.util.ArrayList;
import java.util.List;

public class AdapterExepciones extends RecyclerView.Adapter<AdapterExepciones.HolderExepciones> {

    private Context context;
    private Activity activity;
    private List<Excepcion> List = new ArrayList<Excepcion>();

    public AdapterExepciones(Context context, Activity activity, java.util.List<Excepcion> list) {
        this.context = context;
        this.activity = activity;
        List = list;
    }

    AlertDialog alertDialog = null;



    @NonNull
    @Override
    public AdapterExepciones.HolderExepciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterExepciones.HolderExepciones(LayoutInflater.from(context).inflate(R.layout.item_excepciones, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterExepciones.HolderExepciones holder, final int position) {
        holder.checkBox_Excepcion.setText(List.get(position).getNombre());
        holder.checkBox_Excepcion.setChecked(List.get(position).getCkeck());
       if(!List.get(position).getCkeck()){ holder.imageView_foto.setVisibility(View.INVISIBLE);  holder.imageView_comentario.setVisibility(View.INVISIBLE);}
       if(List.get(position).getEstado().equals("0"))holder.imageView_foto.setVisibility(View.INVISIBLE);

        holder.imageView_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                VarGlobal.ID_Excepcion = List.get(position).getId();
                VarGlobal.NombreExcepcion = List.get(position).getNombre();

                context.startActivity(new Intent(context, FotosActivity.class));
            }
        });

        holder.checkBox_Excepcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VarGlobal.ListnIExepciones.get(position).setCkeck(holder.checkBox_Excepcion.isChecked());
                if (holder.checkBox_Excepcion.isChecked()) {
                    if (!List.get(position).getEstado().equals("0")){holder.imageView_foto.setVisibility(View.VISIBLE); holder.imageView_comentario.setVisibility(View.VISIBLE);}
                    }else{ holder.imageView_foto.setVisibility(View.INVISIBLE); holder.imageView_comentario.setVisibility(View.INVISIBLE);}
            }
        });

        holder.imageView_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater)  activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_comentario_motivo, null);
                final EditText editText =  dialogView.findViewById(R.id.editTextText_comentario);
                Button button_Cancelar = dialogView.findViewById(R.id.button_Cancelar);
                Button  button_Aceptar= dialogView.findViewById(R.id.button_Aceptar);
                editText.setText( VarGlobal.ListnIExepciones.get(position).getComentario());
                button_Aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VarGlobal.ListnIExepciones.get(position).setComentario(editText.getText().toString());
                        alertDialog.dismiss();}
                });


                button_Cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                dialogBuilder.setView(dialogView);
                 alertDialog = dialogBuilder.create();


                alertDialog.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class HolderExepciones extends RecyclerView.ViewHolder {
        CheckBox checkBox_Excepcion;
        ImageView imageView_foto;
        ImageView imageView_comentario;
        public HolderExepciones(@NonNull View itemView) {
            super(itemView);
            checkBox_Excepcion = itemView.findViewById(R.id.checkBox_Excepcion);
            imageView_foto = itemView.findViewById(R.id.imageView_foto);
            imageView_comentario= itemView.findViewById(R.id.imageView_comentario);
        }
    }
}
