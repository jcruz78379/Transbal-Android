package com.example.transmobile.Excepciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.transmobile.Excepciones.Adapters.AdapterExepciones;
import com.example.transmobile.R;
import com.example.transmobile.Global.VarGlobal;

public class ConExepcionActivity extends AppCompatActivity {

    private RecyclerView rvExcepciones = null;
    private TextView textViewChasis = null;
    private TextView textViewClinete = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_exepcion);


        textViewChasis = findViewById(R.id.textViewChasis);
        textViewClinete  = findViewById(R.id.textViewClinete);
        textViewClinete = findViewById(R.id.textViewClinete);
        textViewChasis = findViewById(R.id.textViewChasis);
        textViewChasis.setText(VarGlobal.Exepciones_chasis);
        textViewClinete.setText(VarGlobal.Exepciones_cliente);


        rvExcepciones = findViewById(R.id.rvExcepciones);

        AdapterExepciones adapterListImagenes = new AdapterExepciones(this,this, VarGlobal.ListnIExepciones);
        rvExcepciones.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
        rvExcepciones.setAdapter(adapterListImagenes);



    }
}