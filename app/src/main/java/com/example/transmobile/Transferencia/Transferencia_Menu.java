package com.example.transmobile.Transferencia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
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
import com.example.transmobile.R;
import com.example.transmobile.Home.Home;
import com.example.transmobile.Salida.Modelos.ModeloMotivo;
import com.example.transmobile.Salida.Modelos.ModeloPatio;
import com.example.transmobile.Global.VarGlobal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Transferencia_Menu extends AppCompatActivity {

    private EditText edtBuscar = null;

    private TextView tvFechaActual, tvChasis, tvCliente, tvMotor, tvPignorado, tvDescripcion, tvPatio;

    private Spinner spPatioDestino, spMotivoTrasferencia;

    private Button btnConfirmar2, btnCancelar, btnRegresar;

    private String id_chasis = "";

    private String patio_anterior = "";

    int h = 0;

    private Context context = null;
//    private Activity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia__menu);

        context = this;
//        activity = this;

        edtBuscar = findViewById(R.id.edtPatio);

        tvCliente = findViewById(R.id.tvCliente);
        tvChasis = findViewById(R.id.tvChasis);
        tvMotor = findViewById(R.id.tvMotor);
        tvPignorado = findViewById(R.id.tvPignorado);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvPatio = findViewById(R.id.tvPatio);
        tvFechaActual = findViewById(R.id.tvFechaActual);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnConfirmar2 = findViewById(R.id.btnConfirmar2);
        btnCancelar = findViewById(R.id.btnCancelar);

        spMotivoTrasferencia = findViewById(R.id.spClietne);
        spMotivoTrasferencia.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamemotivo));

        spPatioDestino = findViewById(R.id.spPatioDestino);
        spPatioDestino.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamepatio));


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        tvFechaActual.setText(currentDate);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirmar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertaTransferencia = new AlertDialog.Builder(Transferencia_Menu.this);
                alertaTransferencia.setMessage("Desea Transferir este auto?");
                alertaTransferencia.setCancelable(true);
                alertaTransferencia.setPositiveButton("Si",

                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //usuario, fecha, chasis


                                transferirAuto();
                                dialog.cancel();
                            }
                        });
                alertaTransferencia.setNegativeButton(
                        "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                btnCancelar.performClick();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = alertaTransferencia.create();
                alert11.show();


            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CleanCampos();
            }
        });

        edtBuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
                if ((keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                    edtBuscar.setText(edtBuscar.getText().toString().replaceAll("[^A-Z0-9]+", "").replaceAll("Q","").replaceAll("I","").replaceAll("Ñ","").replaceAll("O","").replaceAll(" ",""));

                    if (edtBuscar.getText().toString().length() > 17){
                        edtBuscar.setText(edtBuscar.getText().toString().substring(0,17));
                    }

                    if (edtBuscar.getText().toString().length() == 17) {
                        validarChasis();
                    } else {
                        Toast.makeText(Transferencia_Menu.this, "El Chasis no es Valido, Debe tener una longitud de 17 caracteres.", Toast.LENGTH_SHORT).show();
                        CleanCampos();
                    }


                }else if(keyCode == KeyEvent.KEYCODE_BACK){
                startActivity(new Intent(context, Home.class));
                finish();
            }
                return true;
            }
        });

        CleanCampos();
    }


    private void validarChasis() {
        VarGlobal.patioList.clear();
        VarGlobal.Listnamepatio.clear();
        VarGlobal.MotivoList.clear();
        VarGlobal.Listnamemotivo.clear();
        spPatioDestino.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamepatio));
        spMotivoTrasferencia.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamemotivo));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.URL + "/" + VarGlobal.transferenciaUrl + "/consulta_Auto_Transferencia.php?ACHASIS=" + edtBuscar.getText().toString(), new Response.Listener<String>() {
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
                                tvMotor.setText(jsonObjectHijo.getString("ANUMMOTOR"));
                                tvPignorado.setText(jsonObjectHijo.getString("PIGNO"));
                                tvDescripcion.setText(jsonObjectHijo.getString("DESCRIP1"));
                                tvPatio.setText(jsonObjectHijo.getString("nombre_patio"));

                                id_chasis = jsonObjectHijo.getString("id");
                                /**/
                            }
                            if (h != 0) {
                                JSONArray jsonArray2 = jsonjObject.getJSONArray("patios");
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject jsonObjectHijo = jsonArray2.getJSONObject(i);

                                    VarGlobal.patioList.add(new ModeloPatio(jsonObjectHijo.getString("id"), jsonObjectHijo.getString("nombre_patio"), jsonObjectHijo.getString("accesspatio")));
                                    VarGlobal.Listnamepatio.add(jsonObjectHijo.getString("nombre_patio"));
                                }

                                JSONArray jsonArray3 = jsonjObject.getJSONArray("motivos");
                                for (int i = 0; i < jsonArray3.length(); i++) {
                                    JSONObject jsonObjectHijo = jsonArray3.getJSONObject(i);

                                    VarGlobal.MotivoList.add(new ModeloMotivo(jsonObjectHijo.getString("id"), jsonObjectHijo.getString("descripcion")));
                                    VarGlobal.Listnamemotivo.add(jsonObjectHijo.getString("descripcion"));

                                }

                                spPatioDestino.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamepatio));
                                spMotivoTrasferencia.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamemotivo));

                            }
                            h++;



                                Toast.makeText(Transferencia_Menu.this, "Datos cargados", Toast.LENGTH_LONG).show();


//                            Imprimo el JSON para validar que la info sale
//                            Toast.makeText(Transferencia_Menu.this, response, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }

                    } catch (Exception e) {
                        Toast.makeText(Transferencia_Menu.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(Transferencia_Menu.this, "El chasis no está en la base de datos", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Transferencia_Menu.this, error.toString(), Toast.LENGTH_LONG).show();

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


    // TRANSFERIR AUTO
    private void transferirAuto() {


        String idPatito = "";
        String patitoNombre = "";
        for (ModeloPatio x : VarGlobal.patioList) {
            if (spPatioDestino.getSelectedItem().toString().equalsIgnoreCase(x.getPatio())) {
                idPatito = x.getId();
                patitoNombre = x.getPatio();

                break;
            }
        }


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.URL + "/" + VarGlobal.transferenciaUrl + "/updateAutoTransferencia.php?id=" + id_chasis + "&patio=" + idPatito + "&motivo_transfer=" + spMotivoTrasferencia.getSelectedItem().toString() + "&username=" + VarGlobal.UserName + "&ANUMMOTOR=" + tvMotor.getText().toString() + "&ACHASIS=" + tvChasis.getText() + "&CNOMCLI=" + tvCliente.getText() + "&DESCRIP1=" + tvDescripcion.getText()  + "&nombre_patio_anterior=" + tvPatio.getText() + "&nombre_patio=" + patitoNombre, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {

                    try {
                        try {
                            JSONObject jsonjObject = new JSONObject(response);


                            if (jsonjObject.getString("Estado1").equalsIgnoreCase("success") && jsonjObject.getString("Estado2").equalsIgnoreCase("success")) {
                                Toast.makeText(Transferencia_Menu.this, "Transferido correctamente", Toast.LENGTH_LONG).show();
                                CleanCampos();
                            } else {
                                Toast.makeText(Transferencia_Menu.this, "Error al transferir", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }

                    } catch (Exception e) {
                        Toast.makeText(Transferencia_Menu.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(Transferencia_Menu.this, "El chasis no está en la base de datos", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Transferencia_Menu.this, error.toString(), Toast.LENGTH_LONG).show();

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

    private void CleanCampos() {
        edtBuscar.setText("");
        tvFechaActual.setText("");
        tvCliente.setText("");
        tvChasis.setText("");
        tvMotor.setText("");
        tvPignorado.setText("");
        tvDescripcion.setText("");
        tvPatio.setText("");


        id_chasis = "";


        VarGlobal.patioList.clear();
        VarGlobal.Listnamepatio.clear();
        VarGlobal.MotivoList.clear();
        VarGlobal.Listnamemotivo.clear();
        spPatioDestino.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamepatio));
        spMotivoTrasferencia.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VarGlobal.Listnamemotivo));


//        startActivity(new Intent(context, Transferencia_Menu.class));
//        activity.finish();

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.salir:

                Toast.makeText(this, "Has pulsado el Menú, Salir", Toast.LENGTH_LONG).show();
                break;

            case android.R.id.home:

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);

                break;
        }

        return true;
    }
}
