package com.example.transmobile.Excepciones;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.transmobile.Excepciones.Modelos.Excepcion;
import com.example.transmobile.Excepciones.Modelos.Imagen;
import com.example.transmobile.R;
import com.example.transmobile.Global.VarGlobal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcepcionesActivity extends AppCompatActivity {

    private EditText editTextChasis = null;

    private TextView tvAutoPorRevisar = null;
    private TextView tvAutoPorVencer = null;
    private TextView tvAutoConExcepcion = null;
    private TextView tvChasis = null;
    private TextView tvCliente = null;


    private CheckBox cbAlfombra = null;
    private CheckBox cbHerramientas = null;
    private CheckBox cbLlaves = null;
    private CheckBox cbNeumatico = null;


    private RadioButton rbNOExcepcion = null;
    private RadioButton rbSIExcepcion = null;


    private Button btnRegistrar = null;

    private LinearLayout LinearLayout_content = null;

    private Context context = null;
    private Activity activity = null;

    private Excepcion _excepcion = null;

    private ProgressDialog progress = null;

    private int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excepciones);


        activity = this;
        context = this;


        editTextChasis = findViewById(R.id.editTextChasis);
        tvAutoPorRevisar = findViewById(R.id.tvAutoPorRevisar);
        tvAutoPorVencer = findViewById(R.id.tvAutoPorVencer);
        tvAutoConExcepcion = findViewById(R.id.tvAutoConExcepcion);
        tvChasis = findViewById(R.id.tvChasis);
        tvCliente = findViewById(R.id.tvCliente);
        cbAlfombra = findViewById(R.id.cbAlfombra);
        cbHerramientas = findViewById(R.id.cbHerramientas);
        cbLlaves = findViewById(R.id.cbLlaves);
        cbNeumatico = findViewById(R.id.cbNeumatico);
        rbNOExcepcion = findViewById(R.id.rbNOExcepcion);
        rbSIExcepcion = findViewById(R.id.rbSIExcepcion);
        btnRegistrar = findViewById(R.id.btnRegistrar);


        progress = new ProgressDialog(this);
        progress.setMessage("Regisitrando Excepción... por favor espere.");


        LinearLayout_content = findViewById(R.id.LinearLayout_content);
        LinearLayout_content.setVisibility(View.GONE);


        tvAutoPorRevisar.setText(String.valueOf(VarGlobal.por_revisar));
        tvAutoPorVencer.setText(String.valueOf(VarGlobal.por_vencer));
        tvAutoConExcepcion.setText(String.valueOf(VarGlobal.vencidos));

        btnRegistrar.setVisibility(View.INVISIBLE);
        rbSIExcepcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegistrar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getBaseContext(), ConExepcionActivity.class));
            }
        });
        rbNOExcepcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegistrar.setVisibility(View.VISIBLE);
            }
        });


        editTextChasis.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_TAB || keyCode == KeyEvent.KEYCODE_ENTER) {

                    editTextChasis.setText(editTextChasis.getText().toString().replaceAll("[^A-Z0-9]+", "").replaceAll("Q","").replaceAll("I","").replaceAll("Ñ","").replaceAll("O","").replaceAll(" ",""));

                    if (editTextChasis.getText().toString().length() > 17){
                        editTextChasis.setText(editTextChasis.getText().toString().substring(0,17));
                    }

                    if (editTextChasis.getText().toString().length() == 17) {
                        limpiarPantalla();
                        validarChasis();
                    } else {
                        Toast.makeText(ExcepcionesActivity.this, "El chasis debe tener 17 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                insertrevision_auto();
            }
        });

    }


    private void validarChasis() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.ConexionIP + "/" + VarGlobal.excepcionUrl + "/consulta_Excepcion.php?chasis=" + editTextChasis.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    try {
                        try {
                            JSONObject jsonjObject = new JSONObject(response);
                            JSONArray jsonArray = jsonjObject.getJSONArray("ss");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray.getJSONObject(i);
                                editTextChasis.setText("");
                                tvChasis.setText(jsonObjectHijo.getString("chasis"));
                                tvCliente.setText(jsonObjectHijo.getString("id_cliente"));

                                VarGlobal.Exepciones_cliente = jsonObjectHijo.getString("id_cliente");
                                VarGlobal.Exepciones_chasis = jsonObjectHijo.getString("chasis");
                                VarGlobal.Modelo = jsonObjectHijo.getString("modelo");
                                VarGlobal.Color = jsonObjectHijo.getString("color");

                                LinearLayout_content.setVisibility(View.VISIBLE);

                            }



                            if (jsonArray.length() == 0) {

                                editTextChasis.setText("");
                                Toast.makeText(ExcepcionesActivity.this, "El chasis ya fue revisado.", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }
                    } catch (Exception e) {
                        Toast.makeText(ExcepcionesActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ExcepcionesActivity.this, "El chasis no está en la base de datos", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ExcepcionesActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void insertrevision_auto() {
        //Inicializamos las variables
        String alfombra = "0";
        String herramientas = "0";
        String llaves = "0";
        String neumatico_repuesto = "0";
        String Status = "0";

        //Validamos la Revisión de los Componentes Obligatorios del Auto...

        if (cbAlfombra.isChecked() && cbHerramientas.isChecked() && cbLlaves.isChecked() && cbNeumatico.isChecked()) {


            if (cbAlfombra.isChecked()) {
                alfombra = "1";
            }
            if (cbHerramientas.isChecked()) {
                herramientas = "1";
            }
            if (cbLlaves.isChecked()) {
                llaves = "1";
            }
            if (cbNeumatico.isChecked()) {
                neumatico_repuesto = "1";
            }
            if (rbSIExcepcion.isChecked() && !rbNOExcepcion.isChecked()) {
                Status = "1";
            }


            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.URL + "/" + VarGlobal.excepcionUrl + "/insertExcepcion.php?tipo=revision_auto&chasis=" + VarGlobal.Exepciones_chasis + "&nombre_cliente=" + VarGlobal.Exepciones_cliente + "&alfombra=" + alfombra + "&herramientas=" + herramientas + "&llaves=" + llaves + "&neumatico_repuesto=" + neumatico_repuesto + "&usuario=" + VarGlobal.UserName + "&excepcion_esatus=" + Status + "&tipo_excepcion=0&foto_ruta=0", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.isEmpty()) {
                        try {
                            try {
                                JSONObject jsonjObject = new JSONObject(response);
                                if (jsonjObject.getString("status2").equalsIgnoreCase("ok")) {
                                    if (rbSIExcepcion.isChecked()) {


                                        //AsyncTaskRunner myTask = new AsyncTaskRunner();
                                        //myTask.execute();
                                        /**/
                                        int w = 0;
                                        for (final Excepcion x : VarGlobal.ListnIExepciones) {  if (x.getCkeck()) { w++; } }
                                        insertExcepcion_motivo();
                                    } else {
                                        progress.dismiss();
                                        limpiarPantalla();
                                        getdatos();
                                        Toast.makeText(ExcepcionesActivity.this, "REVISIÓN - Datos Guardados correctamente", Toast.LENGTH_LONG).show();

                                    }

                                }
                            } catch (JSONException e) {
                                Log.e("Parser JSON", e.toString());
                            }
                        } catch (Exception e) {
                            Toast.makeText(ExcepcionesActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ExcepcionesActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        } else {
            progress.dismiss();
            Toast.makeText(ExcepcionesActivity.this, "Debe Revisar Todos los Accesorios Básicos del Auto", Toast.LENGTH_LONG).show();
        }
    }


    private void insertExcepcion_motivo() {

        final JSONArray jsonArray = new JSONArray();

        for (final Excepcion x : VarGlobal.ListnIExepciones) {
            if (x.getCkeck()) {
                JSONObject jsonExepcion = new JSONObject();
                try {
                    jsonExepcion.put("chasis", VarGlobal.Exepciones_chasis);
                    jsonExepcion.put("id_excepcion", x.getId());
                    jsonExepcion.put("exepcion", x.getNombre());
                    jsonExepcion.put("comentario", x.getComentario());
                    jsonExepcion.put("modelo", VarGlobal.Modelo);
                    jsonExepcion.put("color", VarGlobal.Color);


                    List<Imagen> Listafotos = new ArrayList<Imagen>();
                    String listfotos = "";
                    for (Imagen w : VarGlobal.ListnImagen) {
                        if (w.getID_Excepcion().equalsIgnoreCase(String.valueOf(x.getId()))) {
                            Listafotos.add(w);
                            listfotos = listfotos + w.getName() + ",";
                        }
                    }

                    jsonExepcion.put("foto", listfotos);
                    jsonExepcion.put("totalfotos", String.valueOf(Listafotos.size()));

                    int h = 0;
                    for (Imagen w : Listafotos) {
                        jsonExepcion.put("nombre_foto" + h, w.getName());
                        jsonExepcion.put("foto" + h, readFileAsBase64String(w.getRuta()));
                        h++;
                    }

                    jsonArray.put(jsonExepcion);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } //https://www.w3schools.com/php/php_json.asp
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + VarGlobal.URL + "/" + VarGlobal.excepcionUrl + "/insertExcepcion.php?tipo=excepcion_motivo"+ "&nombre_cliente=" + VarGlobal.Exepciones_cliente +"&usuario=" + VarGlobal.UserName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    try {
                        response = response.replaceAll("\r", "");
                        response = response.replaceAll("\n", "");
                        JSONObject jsonjObject = new JSONObject(response);

                        progress.dismiss();
                        limpiarPantalla();
                        getdatos();
                        Toast.makeText(ExcepcionesActivity.this, "REVISIÓN - Datos guardados correctamente", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e("Parser JSON", e.toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(ExcepcionesActivity.this, "REVISIÓN - Error al guadar los datos", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("data", jsonArray.toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void getdatos() {
        VarGlobal.por_revisar = 0;
        VarGlobal.por_vencer = 0;
        VarGlobal.vencidos = 0;
        VarGlobal.ListnIExepciones.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + VarGlobal.URL + "/" + VarGlobal.excepcionUrl + "/obtenereExepciones.php", new Response.Listener<String>() {
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
                                VarGlobal.ListnIExepciones.add(new Excepcion(jsonObjectHijo.getString("id"), jsonObjectHijo.getString("nombre"), jsonObjectHijo.getString("descripcion"), "", "", jsonObjectHijo.getString("estado"), false));
                            }

                            JSONArray jsonArray2 = jsonjObject.getJSONArray("dashboard");
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray2.getJSONObject(i);
                                int dias = 0;
                                try {
                                    dias = Integer.parseInt(jsonObjectHijo.getString("dias"));
                                } catch (Exception ex) {
                                    dias = VarGlobal.dias_vencer;//igual a 3 para que algan en autos vencidos
                                }


                                if (dias < VarGlobal.dias_por_vencer)
                                    VarGlobal.por_revisar = VarGlobal.por_revisar + 1;
                                else if (dias >= VarGlobal.dias_por_vencer && dias < VarGlobal.dias_vencer)
                                    VarGlobal.por_vencer = VarGlobal.por_vencer + 1;
                                else if (dias >= VarGlobal.dias_vencer)
                                    VarGlobal.vencidos = VarGlobal.vencidos + 1;

                            }

                            VarGlobal.por_revisar = VarGlobal.por_revisar + VarGlobal.por_vencer + VarGlobal.vencidos;

                            Toast.makeText(context, "Datos cargados correctamente", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), ExcepcionesActivity.class));
                            activity.finish();

                        } catch (JSONException e) {
                            Log.e("Parser JSON", e.toString());
                        }


                    } catch (Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                    /**/
                } else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private String readFileAsBase64String(String path) {
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Base64OutputStream b64os = new Base64OutputStream(baos, Base64.DEFAULT);
            byte[] buffer = new byte[8192];
            int bytesRead;
            try {
                while ((bytesRead = is.read(buffer)) > -1) {
                    b64os.write(buffer, 0, bytesRead);
                }
                return baos.toString();
            } catch (IOException e) {
                //Log.e(TAG, "Cannot read file " + path, e);
                // Or throw if you prefer
                return "";
            } finally {
                closeQuietly(is);
                closeQuietly(b64os); // This also closes baos
            }
        } catch (FileNotFoundException e) {
            // Log.e(TAG, "File not found " + path, e);
            // Or throw if you prefer
            return "";
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
        }
    }


    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }


    private void limpiarPantalla() {
        VarGlobal.Exepciones_cliente = "";
        VarGlobal.Exepciones_chasis = "";
        VarGlobal.ListnImagen.clear();
        tvChasis.setText("");
        tvCliente.setText("");
        cbAlfombra.setChecked(false);
        cbHerramientas.setChecked(false);
        cbLlaves.setChecked(false);
        cbNeumatico.setChecked(false);
        rbNOExcepcion.setChecked(false);
        rbSIExcepcion.setChecked(false);
        btnRegistrar.setVisibility(View.INVISIBLE);
        int s = 0;
        for (Excepcion x : VarGlobal.ListnIExepciones) {
            VarGlobal.ListnIExepciones.get(s).setFotos("");
            VarGlobal.ListnIExepciones.get(s).setCkeck(false);
            s++;
        }
        LinearLayout_content.setVisibility(View.INVISIBLE);
    }

}