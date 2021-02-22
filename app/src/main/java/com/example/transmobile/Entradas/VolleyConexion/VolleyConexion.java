package com.example.transmobile.Entradas.VolleyConexion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.transmobile.Entradas.EntradaActivity;
import com.example.transmobile.Entradas.Modelos.mCliente;
import com.example.transmobile.Entradas.Modelos.mColor;
import com.example.transmobile.Entradas.Modelos.mEntrada;
import com.example.transmobile.Entradas.Modelos.mModelo;
import com.example.transmobile.Entradas.Modelos.mPatio;
import com.example.transmobile.Entradas.Modelos.mUsuario;
import com.example.transmobile.Entradas.RealmDB.rCliente;
import com.example.transmobile.Entradas.RealmDB.rEntrada;
import com.example.transmobile.Entradas.RealmDB.rModelo;
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.Login.Login;
import com.example.transmobile.Reimpresion.Modelos.mReimpresion;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static com.example.transmobile.Entradas.RealmDB.rCliente.addClientes;
import static com.example.transmobile.Entradas.RealmDB.rCliente.deleteAllCliente;
import static com.example.transmobile.Entradas.RealmDB.rColor.addColores;
import static com.example.transmobile.Entradas.RealmDB.rColor.deleteAllColor;
import static com.example.transmobile.Entradas.RealmDB.rEntrada.deleteAllEntrada;
import static com.example.transmobile.Entradas.RealmDB.rEntrada.deleteEntradaByChasis;
import static com.example.transmobile.Entradas.RealmDB.rEntrada.getAllEntrada;
import static com.example.transmobile.Entradas.RealmDB.rModelo.addModelos;
import static com.example.transmobile.Entradas.RealmDB.rModelo.deleteAllModelo;
import static com.example.transmobile.Entradas.RealmDB.rPatio.addPatios;
import static com.example.transmobile.Entradas.RealmDB.rPatio.deleteAllPatio;
import static com.example.transmobile.Entradas.RealmDB.rUsuario.addUsuarioes;
import static com.example.transmobile.Entradas.RealmDB.rUsuario.deleteAllUsuario;
import static com.example.transmobile.Reimpresion.RealmDB.rReimpresion.deleteAllReimpresion;
import static com.example.transmobile.Reimpresion.RealmDB.rReimpresion.deleteReimpresionByChais;
import static com.example.transmobile.Reimpresion.RealmDB.rReimpresion.getAllReimpresion;


public class VolleyConexion {
    public static void getData(final Context context) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Cargando datos...");
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ VarGlobal.ConexionIP+"/"+VarGlobal.entradaUrl+"/obtenerDatosEntrada.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()) {

                    try {
                        JSONObject jsonjObject = new JSONObject(response);

                        boolean _clientes = false;
                        boolean _colores = false;
                        boolean _modelos = false;
                        boolean _usuarios = false;
                        boolean _patios = false;

                        try {
                            List<mCliente> Clinetes = new ArrayList<mCliente>();

                            JSONArray dataClinete = jsonjObject.getJSONArray("clientes");
                            for (int i = 0; i < dataClinete.length(); i++) {
                                JSONObject jsonObject = dataClinete.getJSONObject(i);
                                if(i == 0) Clinetes.add(new mCliente("select", "Seleccione"));
                                Clinetes.add(new mCliente(jsonObject.getString("id_cliente"), jsonObject.getString("nombreCliente")));
                            }
                            deleteAllCliente();
                            addClientes(Clinetes);
                            _clientes = true;
                        } catch (Exception ex) {
                            _clientes = false;
                        }


                        try {
                            List<mColor> Clores = new ArrayList<mColor>();
                            JSONArray dataClores = jsonjObject.getJSONArray("colores");
                            for (int i = 0; i < dataClores.length(); i++) {
                                JSONObject jsonObject = dataClores.getJSONObject(i);
                                if(i == 0) Clores.add(new mColor("select", "Seleccione"));
                                Clores.add(new mColor(jsonObject.getString("id_color"), jsonObject.getString("nombreColor")));
                            }
                            deleteAllColor();
                            addColores(Clores);
                            _colores = true;
                        } catch (Exception ex) {
                            _colores = false;
                        }


                        try {
                            List<mModelo> Modelos = new ArrayList<mModelo>();
                            JSONArray dataModelos = jsonjObject.getJSONArray("modelos");
                            for (int i = 0; i < dataModelos.length(); i++) {
                                JSONObject jsonObject = dataModelos.getJSONObject(i);
                                if(i == 0) Modelos.add(new mModelo("select", "Seleccione", "select","select"));
                                Modelos.add(new mModelo(jsonObject.getString("id_modelo"), jsonObject.getString("nombreModelo"), jsonObject.getString("id_color"), jsonObject.getString("id_cliente")));
                            }
                            deleteAllModelo();
                            addModelos(Modelos);
                            _modelos = true;
                        } catch (Exception ex) {
                            _modelos = false;
                        }


                        try {
                            List<mUsuario> Usuarios = new ArrayList<mUsuario>();
                            JSONArray dataUsuarios = jsonjObject.getJSONArray("usuarios");
                            for (int i = 0; i < dataUsuarios.length(); i++) {
                                JSONObject jsonObject = dataUsuarios.getJSONObject(i);
                                Usuarios.add(new mUsuario(jsonObject.getString("id_usuario"), jsonObject.getString("username"), jsonObject.getString("pass")));
                            }
                            deleteAllUsuario();
                            addUsuarioes(Usuarios);
                            _usuarios = true;
                        } catch (Exception ex) {
                            _usuarios = false;
                        }


                        try {
                            List<mPatio> Patios = new ArrayList<mPatio>();
                            JSONArray dataPatio = jsonjObject.getJSONArray("patios");
                            for (int i = 0; i < dataPatio.length(); i++) {
                                JSONObject jsonObject = dataPatio.getJSONObject(i);
                                if(i == 0) Patios.add(new mPatio("select", "Seleccione"));
                                Patios.add(new mPatio(jsonObject.getString("id_patio"), jsonObject.getString("nombrepatio")));
                            }
                            deleteAllPatio();
                            addPatios(Patios);
                            _patios = true;
                        } catch (Exception ex) {
                            _patios = false;
                        }


                    } catch (JSONException e) {
                        Log.e("Parser JSON", e.toString());
                    }


                    Toast.makeText(context, "Cargado correctamente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Error al obtener datos", Toast.LENGTH_LONG).show();
                }
                progress.dismiss();
                //Intent intent = new Intent(context, EntradaActivity.class);
                //context.startActivity(intent);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                // parametros.put("usuario", "");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public static void setEntrada(final Context context) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Cargando datos...");
        progress.show();
        final JSONArray jsonArray = new JSONArray();
        for (final mEntrada x : getAllEntrada()) {

            JSONObject jsonEntrada = new JSONObject();
            try {
                jsonEntrada.put("id_cliente", x.getId_clinete());
                jsonEntrada.put("chasis", x.getChasis());
                jsonEntrada.put("licenciaConductor", x.getLicenciaConductor());
                jsonEntrada.put("cedulaConductor", x.getCedulaConductor());
                jsonEntrada.put("nombreConductor", x.getNombreCondutor());
                jsonEntrada.put("id_usuario", x.getId_usuario());
                jsonEntrada.put("fecha", x.getFecha());
                jsonEntrada.put("x", x.getObrevaciones());
                jsonEntrada.put("id_patio", x.getId_patio());
                jsonEntrada.put("gasolina", x.getGasolina());
                jsonEntrada.put("kilometraje", x.getKilometraje());
                jsonEntrada.put("id_modelo", x.getId_modelo());
                jsonEntrada.put("id_color", x.getId_color());
                jsonEntrada.put("impresa", x.getImpresa());

                jsonArray.put(jsonEntrada);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ VarGlobal.URL+"/"+VarGlobal.entradaUrl+"/insertarEntrada.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    try {

                        JSONObject jsonjObject = new JSONObject(response);
                        deleteAllEntrada();
                        VarGlobal.numconteo.setText("Total de entradas por subir: "+String.valueOf(rEntrada.getAllEntrada().size()));
                        Toast.makeText(context, "Entradas - Datos guardados correctamente", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e("Parser JSON", e.toString());
                    }
                }
                progress.dismiss();
                VolleyConexion.setRemprecion(context, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                VolleyConexion.setRemprecion(context, true);
                Toast.makeText(context, "Entradas - Error al guadar los datos", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("data", jsonArray.toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public static void setEntrada2(final Context context) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Cargando datos...");
        // progress.show();
        final JSONArray jsonArray = new JSONArray();
        for (final mEntrada x : getAllEntrada()) {

            JSONObject jsonEntrada = new JSONObject();
            try {
                jsonEntrada.put("id_cliente", x.getId_clinete());
                jsonEntrada.put("chasis", x.getChasis());
                jsonEntrada.put("licenciaConductor", x.getLicenciaConductor());
                jsonEntrada.put("cedulaConductor", x.getCedulaConductor());
                jsonEntrada.put("nombreConductor", x.getNombreCondutor());
                jsonEntrada.put("id_usuario", x.getId_usuario());
                jsonEntrada.put("fecha", x.getFecha());
                jsonEntrada.put("x", x.getObrevaciones());
                jsonEntrada.put("id_patio", x.getId_patio());
                jsonEntrada.put("gasolina", x.getGasolina());
                jsonEntrada.put("kilometraje", x.getKilometraje());
                jsonEntrada.put("id_modelo", x.getId_modelo());
                jsonEntrada.put("id_color", x.getId_color());
                jsonEntrada.put("impresa", x.getImpresa());

                jsonArray.put(jsonEntrada);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ VarGlobal.ConexionIP+"/"+VarGlobal.entradaUrl+"/insertarEntrada2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    try {
                        JSONObject jsonjObject = new JSONObject(response);
                        JSONArray data = jsonjObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);
                            deleteEntradaByChasis(j.getString("chasis"));
                        }

                        Toast.makeText(context, "Entradas - Datos guardados correctamente", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e("Parser JSON", e.toString());
                    }
                }
                // progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progress.dismiss();
                Toast.makeText(context, "Entradas - Error al guadar los datos", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("data", jsonArray.toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    public static void setRemprecion(final Context context, final boolean showProgressBar) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Enviando reimpresiones...");
        if(showProgressBar) progress.show();
        final JSONArray jsonArray = new JSONArray();
        for (final mReimpresion x : getAllReimpresion()) {

            JSONObject jsonEntrada = new JSONObject();
            try {
                jsonEntrada.put("id_patio", x.getId_patio());
                jsonEntrada.put("patio", x.getPatio());
                jsonEntrada.put("id_cliente", x.getId_cliente());
                jsonEntrada.put("cliente", x.getCliente());
                jsonEntrada.put("chasis", x.getChasis());
                jsonEntrada.put("usado", x.getUsado());
                jsonEntrada.put("placa", x.getPlaca());
                jsonEntrada.put("usuario", x.getUsuario());
                jsonEntrada.put("fecha_Imprecion", x.getFecha_Imprecion());


                jsonArray.put(jsonEntrada);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ VarGlobal.ConexionIP+"/"+VarGlobal.reimpresionUrl+"/insertarReimprecion.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    try {
                        JSONObject jsonjObject = new JSONObject(response);
                        JSONArray data = jsonjObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);

                        }

                        Toast.makeText(context, "Reimpresion - Datos guardados correctamente", Toast.LENGTH_LONG).show();
                        deleteAllReimpresion();
                    } catch (JSONException e) {
                        Log.e("Parser JSON", e.toString());
                    }
                }
                if(showProgressBar)  progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(showProgressBar)  progress.dismiss();
                Toast.makeText(context, "Reimpresion - Error al guadar los datos", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("data", jsonArray.toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}
