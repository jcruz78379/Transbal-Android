package com.example.transmobile.Inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.Home.Home;
import com.example.transmobile.Inventario.Adapter.AdapterInventario;
import com.example.transmobile.Inventario.Modelo.mItemInventario;
import com.example.transmobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventarioActivity2 extends AppCompatActivity {


    private TextView tvFechaActual = null;
    private EditText edtBuscar = null;
    private  EditText etUbicacion;
    private TextView tvChasis, tvCliente;
    private Button btnGuardar = null;
    private RecyclerView RecyclerViewInventario = null;
    private Context context = null;
    private ProgressDialog progress = null;
    private Spinner spPatioDestino2;




    private String id = "";
    private String ACHASIS = "";
    private String CNOMCLI= "";
    private String ANUMMOTOR= "";
    private String ANUMCERT = "";
    private String PIGNO = "";
    private String DESCRIP1 = "";
    private String nombre_patio = "";
    private String accesspatio= "";
    private String liquidado= "";
    private String fechaRegistroPDT= "";


    private List<mItemInventario> listInventario = new ArrayList<mItemInventario>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario2);

        // Ocultamos La barra de acción





        context = this;


        tvFechaActual = findViewById(R.id.tvFechaActual);
        edtBuscar = findViewById(R.id.edtPatio);
        etUbicacion = findViewById(R.id.etUbicacion);
        tvChasis = findViewById(R.id.tvChasis);
        tvCliente = findViewById(R.id.tv_cliente);
        btnGuardar = findViewById(R.id.btnGuardar);
        RecyclerViewInventario = findViewById(R.id.RecyclerViewInventario);
        spPatioDestino2 = findViewById(R.id.sp_patio);
        getListPatio();

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        tvFechaActual.setText(currentDate);


        //Validar Chasis
        etUbicacion.requestFocus();
        edtBuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if ((keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                            edtBuscar.setText(edtBuscar.getText().toString().replaceAll("[^A-Z0-9]+", "").replaceAll("Q","").replaceAll("I","").replaceAll("Ñ","").replaceAll("O","").replaceAll(" ",""));

                            if (edtBuscar.getText().toString().length() > 17){
                                edtBuscar.setText(edtBuscar.getText().toString().substring(0,17));
                            }

                            if (edtBuscar.getText().toString().length() == 17) {
                                validarChasis();
                            } else {
                                Toast.makeText(context, "El Chasis no es Valido, Debe tener una longitud de 17 caracteres.", Toast.LENGTH_SHORT).show();
                                Limpiarpantalla();
                            }


                        }else if(keyCode == KeyEvent.KEYCODE_BACK){
                            startActivity(new Intent(context, Home.class));
                            finish();
                        }

                return true;
            }
        });

        /* Evento OnClick BOTON GUARDAR */
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistrarConteoAuto();


            }
        });  /* FIN DEL Evento OnClick BOTON GUARDAR */
/**/
    }


    ///////////////////////////////////////////////////////////////////////////////////
    private void validarChasis() {

//        progress = new ProgressDialog(context);
//        progress.setMessage("Cargando datos...");
//        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal._ConexionRute3 + "/obtenerChasis_Inventario.php?ACHASIS=" + edtBuscar.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {

                    try {
                        try {
                            JSONObject jsonjObject = new JSONObject(response);

                            JSONArray jsonArray = jsonjObject.getJSONArray("chasis");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray.getJSONObject(i);

                                edtBuscar.setText(jsonObjectHijo.getString("ACHASIS"));

                                tvChasis.setText(jsonObjectHijo.getString("ACHASIS"));
                                tvCliente.setText(jsonObjectHijo.getString("CNOMCLI"));

                                id = jsonObjectHijo.getString("id"); //0
                                ACHASIS = jsonObjectHijo.getString("ACHASIS"); //1
                                CNOMCLI= jsonObjectHijo.getString("CNOMCLI"); //2
                                ANUMMOTOR= jsonObjectHijo.getString("ANUMMOTOR"); //3
                                ANUMCERT = jsonObjectHijo.getString("ANUMCERT"); //4
                                DESCRIP1 = jsonObjectHijo.getString("DESCRIP1"); //5
                                nombre_patio = jsonObjectHijo.getString("nombre_patio"); //6
                                accesspatio= jsonObjectHijo.getString("accesspatio"); //7
                                liquidado= jsonObjectHijo.getString("liquidado"); //8

                            }

                            // btnGuardar.setEnabled(true);
                            if( jsonArray.length() <= 0){

                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case DialogInterface.BUTTON_POSITIVE:
                                                tvChasis.setText(edtBuscar.getText().toString());
                                                tvCliente.setText("CLIENTE NO ASIGNADO");
                                                id = "0";
                                                ACHASIS =   tvChasis.getText().toString() ;
                                                CNOMCLI=  tvCliente.getText().toString();
                                                ANUMMOTOR=  "0";
                                                ANUMCERT =  "0";
                                                DESCRIP1 =  "0";
                                                nombre_patio = spPatioDestino2.getSelectedItem().toString();
                                                accesspatio=  "0";
                                                liquidado=  "0";
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                edtBuscar.setText("");
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("El Chasis no esta en la base de datos. \nDesea guardarlo de todas formas?")
                                        .setPositiveButton("Si", dialogClickListener)
                                        .setNegativeButton("No", dialogClickListener)
                                        .show();

                            }else{
                                Toast.makeText(context, "Chasis CARGADO...", Toast.LENGTH_LONG).show();
                            }

//                            progress.dismiss();
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }

                    } catch (Exception e) {
//                        progress.dismiss();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }

                } else {
//                    progress.dismiss();
                    Toast.makeText(context, "El chasis no está en la base de datos", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @NonNull
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
//                progress.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("ACHASIS", edtBuscar.getText().toString());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    ////////////////////////////////////////////////////////////////

    private void RegistrarConteoAuto(){

        progress = new ProgressDialog(context);
        progress.setMessage("Registrando datos de Conteo...");
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal._ConexionRute3 + "/insertConteoAuto.php", new Response.Listener<String>(){


            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()) {
                    try {
                        JSONObject jsonjObject = new JSONObject(response);

                        listInventario.add(new mItemInventario(ACHASIS, CNOMCLI, fechaRegistroPDT));
                        AdapterInventario adapterListInventario= new AdapterInventario(context,listInventario);
                        LinearLayoutManager LinearLayoutManager =  new LinearLayoutManager(context);
                        LinearLayoutManager.setReverseLayout(true);
                        RecyclerViewInventario.setLayoutManager(LinearLayoutManager);
                        RecyclerViewInventario.setAdapter(adapterListInventario);
                        RecyclerViewInventario.scrollToPosition(listInventario.size() - 1);

                        progress.dismiss();
                        Toast.makeText(context, "Conteo REGISTRADO correctamente", Toast.LENGTH_LONG).show();
                        Limpiarpantalla();
                    } catch (JSONException e) {
                        Log.e("Parser JSON", e.toString());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                progress.dismiss();
                Limpiarpantalla();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Calendar mcurrentTime = Calendar.getInstance();


                int year = mcurrentTime.get(Calendar.YEAR);
                int month = mcurrentTime.get(Calendar.MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int seconds = mcurrentTime.get(Calendar.SECOND);
                String fecha = year+"-"+(month+1)+"-"+day;
                String _seconds = String.valueOf(seconds);
                if(_seconds.length() == 1)  _seconds = "0"+_seconds;
                String hora = hour+":"+minute+":"+_seconds;


                fechaRegistroPDT =  fecha + " " + hora;

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_chasis", id);
                parametros.put("chasis", ACHASIS);
                parametros.put("nombre_cliente", CNOMCLI);
                parametros.put("motor", ANUMMOTOR);
                parametros.put("num_certificado", ANUMCERT);
                parametros.put("descripcion", DESCRIP1);
                parametros.put("patio_logico", nombre_patio);
                parametros.put("accesspatio", accesspatio);
                parametros.put("liquidado", liquidado);
                parametros.put("patio_fisico", spPatioDestino2.getSelectedItem().toString());
                parametros.put("ubicacion", etUbicacion.getText().toString());
                parametros.put("nombre_usuario",  VarGlobal.UserName);
                parametros.put("fecha_registro",fechaRegistroPDT );


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void getListPatio() {

        progress = new ProgressDialog(context);
        progress.setMessage("Cargando datos...");
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal.ConexionRute + "/ObtenerPatios.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Home.this, response, Toast.LENGTH_LONG).show();
                if (!response.isEmpty()) {

                        try {
                            JSONObject jsonjObject = new JSONObject(response);
                            JSONArray jsonArray = jsonjObject.getJSONArray("patios");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray.getJSONObject(i);
                                VarGlobal.Listnamepatio.add(jsonObjectHijo.getString("nombre_patio"));
                            }

                            spPatioDestino2.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamepatio));
                            Toast.makeText(context, "Datos cargados correctamente", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }
                } else {
                    Toast.makeText(context, "El chasis no está en la base de datos", Toast.LENGTH_LONG).show();
                }

                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("", "");

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    // METODO PARA LIMPIAR PANTALLA
    private void Limpiarpantalla() {
        edtBuscar.setText("");
        tvChasis.setText("");
        tvCliente.setText("");

        id = "";
        ACHASIS = "";
        CNOMCLI= "";
        ANUMMOTOR= "";
        ANUMCERT = "";
        PIGNO = "";
        DESCRIP1 = "";
        nombre_patio = "";
        accesspatio= "";
        liquidado= "";

    }


}