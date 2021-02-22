package com.example.transmobile.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.transmobile.Entradas.EntradaActivity;
import com.example.transmobile.Excepciones.ExcepcionesActivity;
import com.example.transmobile.Excepciones.Modelos.Excepcion;
import com.example.transmobile.Inventario.InventarioActivity;
import com.example.transmobile.Inventario.InventarioActivity2;
import com.example.transmobile.R;
import com.example.transmobile.Reimpresion.ReimpresionActivity;
import com.example.transmobile.Salida.Modelos.ModeloMotivo;
import com.example.transmobile.Salida.Modelos.ModeloPatio;
import com.example.transmobile.Salida.SalidaAuto;
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.Transferencia.Transferencia_Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    Button btnSalida, btnEntrada, btnTransferencia, btnExcepciones, btnInventarioActivity, btnReimpresion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnSalida = findViewById(R.id.btnSalidaActivity);
        btnTransferencia = findViewById(R.id.btnTransferenciaMenu);
        btnExcepciones = findViewById(R.id.btnExcepciones);
        btnInventarioActivity = findViewById(R.id.btnInventario);
        btnEntrada = findViewById(R.id.btnEntrada);
        btnReimpresion = findViewById(R.id.btnReimpresion);

        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SalidaAuto.class);
                startActivity(intent);
            }
        });

        btnTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Transferencia_Menu.class);
                startActivity(intent);
            }
        });

        btnExcepciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExcepcionesActivity.class);
                startActivity(intent);
            }
        });

        btnInventarioActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InventarioActivity2.class);
                startActivity(intent);
            }
        });

        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EntradaActivity.class);
                startActivity(intent);
            }
        });

        btnReimpresion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReimpresionActivity.class);
                startActivity(intent);
            }
        });

       // getListPatio();

        getdatos();
    }



    private void getListPatio() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal.ConexionRute + "/ObtenerPatios.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Home.this, response, Toast.LENGTH_LONG).show();
                if (!response.isEmpty()) {
                    try {
                        try {
                            JSONObject jsonjObject = new JSONObject(response);
                            JSONArray jsonArray = jsonjObject.getJSONArray("patios");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray.getJSONObject(i);


                                VarGlobal.patioList.add(new ModeloPatio(jsonObjectHijo.getString("id"), jsonObjectHijo.getString("nombre_patio"), jsonObjectHijo.getString("accesspatio")));
                                VarGlobal.Listnamepatio.add(jsonObjectHijo.getString("nombre_patio"));
                            }
                            JSONArray jsonArray2 = jsonjObject.getJSONArray("motivos");
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray2.getJSONObject(i);


                                VarGlobal.MotivoList.add(new ModeloMotivo(jsonObjectHijo.getString("id"), jsonObjectHijo.getString("descripcion")));
                                VarGlobal.Listnamemotivo.add(jsonObjectHijo.getString("descripcion"));

                            }

                            Toast.makeText(Home.this, "Datos cargados correctamente", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }


                    } catch (Exception e) {
                        Toast.makeText(Home.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(Home.this, "El chasis no está en la base de datos", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home.this, error.toString(), Toast.LENGTH_LONG).show();

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

    private void getdatos(){
        VarGlobal.por_revisar = 0;
        VarGlobal.por_vencer = 0;
        VarGlobal.vencidos = 0;
        VarGlobal.ListnIExepciones.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal.ConexionRute + "/obtenereExepciones.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Home.this, response, Toast.LENGTH_LONG).show();
                if (!response.isEmpty()) {
                    try {
                        try {
                            JSONObject jsonjObject = new JSONObject(response);
                            JSONArray jsonArray = jsonjObject.getJSONArray("excepciones");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray.getJSONObject(i);
                                VarGlobal.ListnIExepciones.add(new Excepcion(jsonObjectHijo.getString("id"), jsonObjectHijo.getString("nombre"), jsonObjectHijo.getString("descripcion"),"","", jsonObjectHijo.getString("estado"), false));
                            }

                            JSONArray jsonArray2 = jsonjObject.getJSONArray("dashboard");
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray2.getJSONObject(i);
                                int dias = 0;
                                try{
                                    dias = Integer.parseInt(jsonObjectHijo.getString("dias"));
                                }catch (Exception ex){
                                    dias = VarGlobal.dias_vencer;//igual a 3 para que algan en autos vencidos
                                }

                                //Condición para Entrantes, por Vencer y Vencidos
                                if(dias <  VarGlobal.dias_por_vencer) VarGlobal.por_revisar =  VarGlobal.por_revisar + 1;
                                else if(dias >=  VarGlobal.dias_por_vencer && dias <  VarGlobal.dias_vencer) VarGlobal.por_vencer =  VarGlobal.por_vencer + 1;
                                else if(dias >=  VarGlobal.dias_vencer) VarGlobal.vencidos =  VarGlobal.vencidos + 1;

                            }
                            VarGlobal.por_revisar =  VarGlobal.por_revisar +  VarGlobal.por_vencer + VarGlobal.vencidos;
                            Toast.makeText(Home.this, "Datos cargados correctamente", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }


                    } catch (Exception e) {
                        Toast.makeText(Home.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                    /**/
                } else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
