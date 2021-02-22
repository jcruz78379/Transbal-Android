package com.example.transmobile.Inventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.transmobile.Home.Home;
import com.example.transmobile.Inventario.Modelo.mItemInventario;
import com.example.transmobile.R;
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.Salida.Modelos.ModeloMotivo;
import com.example.transmobile.Salida.Modelos.ModeloPatio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventarioActivity extends AppCompatActivity {

    private TextView tvFechaActual = null;
    private EditText edtBuscar = null;
    private TextView tv_codbar = null;
    private TextView tv_articulo = null;
    private EditText etCantidad = null;
    private TextView tvChasis, tvCliente, tvMotor, tvPignorado, tvDescripcion, tvPatio;
    private Button btnGuardar = null;
    private RecyclerView RecyclerViewInventario = null;
    private List<mItemInventario> mArticuloList = new ArrayList<mItemInventario>();
    private Context context = null;
    private ProgressDialog progress = null;

    private String id_chasis = "";
    private Spinner spPatioDestino2;

    int h = 0;


    private String id_producto = "";
    private String codigo = "";
    private String referencia = "";
    private String codigo_barra = "";
    private String codigo_barras2 = "";
    private String producto = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        // Ocultamos La barra de acción


        context = this;

        getListPatio();
        tvFechaActual = findViewById(R.id.tvFechaActual);
        edtBuscar = findViewById(R.id.edtPatio);
        tvChasis = findViewById(R.id.tvChasis);
        tvCliente = findViewById(R.id.tv_cliente);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setEnabled(false);

        RecyclerViewInventario = findViewById(R.id.RecyclerViewInventario);

        spPatioDestino2 = findViewById(R.id.spPatioDestino2);
        spPatioDestino2.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamepatio));

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        tvFechaActual.setText(currentDate);


        //Validar Chasis
        edtBuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                try {

                    if (!(spPatioDestino2.getSelectedItem().toString().equals("") || spPatioDestino2.getSelectedItem().toString().equals(null))) {
                        if ((keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                            edtBuscar.setText(edtBuscar.getText().toString().replaceAll("[^A-Z0-9]+", ""));

                            if (edtBuscar.getText().toString().length() == 17) {
                                validarChasis();
                            } else {
                                Toast.makeText(InventarioActivity.this, "El Chasis no es Valido, Debe tener una longitud de 17 caracteres.", Toast.LENGTH_SHORT).show();
                                Limpiarpantalla();
                            }/**/


                        }

                    } else {
                        Toast.makeText(context, "Dede seleccionar el patio en donde está ubicado el chasis", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception ex) {
                    Toast.makeText(context, "Dede seleccionar el patio en donde está ubicado el chasis", Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });


        // GUARDAR

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(context, "test", Toast.LENGTH_LONG).show();

/*
                if(!etCantidad.getText().toString().equals("")){

                    progress = new ProgressDialog(context);
                    progress.setMessage("Cargando datos...");
                    progress.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal.ConexionRute + "/insertConteo.php?codigo="+codigo+"&referencia="+referencia+"&codigo_barra="+codigo_barra+"&codigo_barras2="+codigo_barras2+"&cantidad="+etCantidad.getText().toString()+"&descripcion="+producto+"&usuario="+VarGlobal.UserName, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonjObject = new JSONObject(response);

                                if(jsonjObject.getString("status").equals("ok")){
                                    mArticuloList.add(new mArticulo(
                                            String.valueOf(mArticuloList.size() + 1),
                                            tv_codbar.getText().toString(),
                                            tv_articulo.getText().toString(),
                                            Integer.parseInt(etCantidad.getText().toString()),
                                            tvFechaActual.getText().toString()));
                                    AdapterInventario adapterInventario = new AdapterInventario(context, mArticuloList);
                                    RecyclerViewInventario.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));
                                    RecyclerViewInventario.setAdapter(adapterInventario);

                                }else{
                                    Toast.makeText(context, "Error al guardar los datos",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context, "Error al guardar los datos",Toast.LENGTH_LONG).show();
                            }

                            progress.dismiss();
                            Limpiarpantalla();
                            edtBuscar.setText("");
                            edtBuscar.requestFocus();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismiss();
                            Toast.makeText(context, "Error ==>"+error.getMessage(),Toast.LENGTH_LONG).show();
                            Limpiarpantalla();
                            edtBuscar.setText("");
                            edtBuscar.requestFocus();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(context, "Debe ingresar una cantidad para poder guardar.", Toast.LENGTH_LONG).show();
                }
                /**/

            }
        });


    }



    @Override
    public void onBackPressed() {

        startActivity(new Intent(context, Home.class));
        finish();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    private void validarChasis() {
    /*    VarGlobal.patioList.clear();
        VarGlobal.Listnamepatio.clear();
        VarGlobal.MotivoList.clear();
        VarGlobal.Listnamemotivo.clear();
        spPatioDestino2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamepatio));
/**/
        progress = new ProgressDialog(context);
        progress.setMessage("Cargando datos...");
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal.ConexionRute + "/consulta_Auto_Transferencia.php?ACHASIS=" + edtBuscar.getText().toString(), new Response.Listener<String>() {
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

                                /**/
                            }

                            progress.dismiss();
                            // btnGuardar.setEnabled(true);
                            Toast.makeText(InventarioActivity.this, "Chasis cargado", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }

                    } catch (Exception e) {
                        progress.dismiss();
                        Toast.makeText(InventarioActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    progress.dismiss();
                    //Toast.makeText(InventarioActivity.this, "El chasis no está en la base de datos", Toast.LENGTH_LONG).show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(InventarioActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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

//    ;


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

                            Toast.makeText(context, "Datos cargados correctamente", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }


                    } catch (Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
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
        tv_codbar.setText("");
        tv_articulo.setText("");
        etCantidad.setText("");
    }
}