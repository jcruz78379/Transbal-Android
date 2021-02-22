package com.example.transmobile.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.transmobile.Entradas.RealmDB.rEntrada;
import com.example.transmobile.Entradas.VolleyConexion.VolleyConexion;
import com.example.transmobile.Home.Home;
import com.example.transmobile.R;
import com.example.transmobile.Global.VarGlobal;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Login extends AppCompatActivity {


    EditText editTextUsuario;
    EditText editTextPassword ;
    TextView textView_validador,tvFechaHoy, tvTotal_entradas;
    Button btnLogin, btnSincronizar, btnSubirEntradas;

    private Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Ocultamos La barra de acción


        context = this;

        Realm.init(context);
        RealmConfiguration configuration =
                new RealmConfiguration.Builder()
                        .name(Realm.DEFAULT_REALM_NAME)
                        .schemaVersion(0)
                        .deleteRealmIfMigrationNeeded()
                        .allowWritesOnUiThread(true)
                        .build();
        Realm.setDefaultConfiguration(configuration);


        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);
        textView_validador = findViewById(R.id.textView_validador);
        tvFechaHoy = findViewById(R.id.tvFechaHoy);
        btnLogin = findViewById(R.id.btnLogin);
        btnSincronizar = findViewById(R.id.btnSincronizar);
        tvTotal_entradas = findViewById(R.id.tvTotal_entradas);
        btnSubirEntradas = findViewById(R.id.btnSubirEntradas);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        tvFechaHoy.setText(currentDate);

        tvTotal_entradas.setText("Total de entradas por subir: "+String.valueOf(rEntrada.getAllEntrada().size()));
        VarGlobal.numconteo = tvTotal_entradas;
        btnSubirEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyConexion.setEntrada(context);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUsuario.getText().toString().equals("")){

//                    textView_validador.setText("Debe ingresar el usuario");
                    Toast.makeText(getApplicationContext(),"Debe ingresar el usuario", Toast.LENGTH_LONG).show();

                }

                if (editTextPassword.getText().toString().equals("")){

//                    textView_validador.setText("Debe ingresar la contaseña");
                    Toast.makeText(getApplicationContext(),"Debe ingresar la contaseña", Toast.LENGTH_LONG).show();


                }

                if (editTextPassword.getText().toString().equals("") && editTextPassword.getText().toString().equals("")){

//                    textView_validador.setText("Ingrese Usuario y Contaseña");
                    Toast.makeText(getApplicationContext(),"Ingrese Usuario y Contaseña", Toast.LENGTH_LONG).show();

                } else {

                    validarUsuario("http://"+ VarGlobal.ConexionIP+"/"+VarGlobal.ConexionRute+"/validar_usuario.php?usuario="+editTextUsuario.getText().toString()+"&password="+editTextPassword.getText().toString());

                    // Modo Desarrollo
//                    validarUsuario("http://localhost/WebService/Transbal/Salida_Auto/validar_usuario.php");

                }
            }

        });

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyConexion.getData(context);
            }
        });

    } //Final del OnCreate


    // Metodo para Validar el Usuario
    private void validarUsuario(String URL){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()){

                    try {
                        JSONObject jsonjObject = new JSONObject(response);
                        if (jsonjObject.getString("status").equalsIgnoreCase("ok")) {
                            VarGlobal.UserName =  editTextUsuario.getText().toString();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Login.this,"El Usuario o la contaseña, son Incorrectos", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Log.e("Parser JSON", e.toString());
                    }
                } else {

                    Toast.makeText(Login.this,"El Usuario o la contaseña, son Incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("usuario", editTextUsuario.getText().toString());
                parametros.put("password", editTextPassword.getText().toString());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
