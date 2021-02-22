package com.example.transmobile.Entradas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.transmobile.Entradas.Modelos.mEntrada;
import com.example.transmobile.Entradas.RealmDB.rCliente;
import com.example.transmobile.Entradas.RealmDB.rColor;
import com.example.transmobile.Entradas.RealmDB.rModelo;
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.R;
import java.util.ArrayList;
import java.util.List;

import static com.example.transmobile.Entradas.EntradaActivity.GetCurrentTimeStamp2;

public class AdapterReimprecionEntrada extends RecyclerView.Adapter<AdapterReimprecionEntrada.HolderReimpresionEntrada>{
    private Context context;
    private List<mEntrada>List = new ArrayList<mEntrada>();

    public AdapterReimprecionEntrada(Context context, List<mEntrada> list) {
        this.context = context;
        List = list;

    }

    @NonNull
    @Override
    public HolderReimpresionEntrada onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderReimpresionEntrada(LayoutInflater.from(context).inflate(R.layout.item_rv_reimpresion_entrada, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderReimpresionEntrada holder, final int position) {
        holder.textView_cliente.setText(List.get(position).getId_clinete());
        holder.textView_modelo.setText(List.get(position).getId_modelo());
        holder.textView_color.setText(List.get(position).getId_color());
        holder.textView_chasis.setText(List.get(position).getChasis());
        String txt = (List.get(position).getImpresa()==null)?"No":List.get(position).getImpresa();

        holder.textView_impreso.setText("Impreso: "+ txt);
        holder.CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VarGlobal.editText.setText(List.get(position).getId_clinete()+
                        "|" + List.get(position).getChasis() +
                        "|" + List.get(position).getChasis().substring(11, 17) +
                        "|" + GetCurrentTimeStamp2());
                VarGlobal.mPrint.performClick();
            }
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class HolderReimpresionEntrada extends RecyclerView.ViewHolder {
        private TextView textView_cliente, textView_chasis, textView_modelo, textView_color, textView_impreso;
        private CardView CardView;
        public HolderReimpresionEntrada(@NonNull View itemView) {
            super(itemView);
            textView_cliente = itemView.findViewById(R.id.textView_cliente);
            textView_chasis = itemView.findViewById(R.id.textView_chasis);
            textView_modelo = itemView.findViewById(R.id.textView_modelo);
            textView_color = itemView.findViewById(R.id.textView_color);
            textView_impreso = itemView.findViewById(R.id.textView_impreso);
            CardView = itemView.findViewById(R.id.CardView);
        }
    }
}