package com.example.transmobile.Salida;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.R;
import com.example.transmobile.Salida.Modelos.ModeloSalida;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SalidaAuto extends AppCompatActivity {

    private EditText edtBuscar = null;
    private ModeloSalida salida = null;

    private TextView tvChasis, tvCliente, tvMotor, tvColor, tvDescripcion, tvPatio, tvNumLiquidacion, tvNumCertificado, tvNumOEntrada, tvFechaActual;
    private Button btnConfirmar, btnCancelar;

    private String id_chasis = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida_auto);



        edtBuscar = findViewById(R.id.edtPatio);


        tvChasis = findViewById(R.id.tvChasis);
        tvCliente = findViewById(R.id.tvCliente);
        tvMotor = findViewById(R.id.tvMotor);
        tvColor = findViewById(R.id.tvColor);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvPatio = findViewById(R.id.tvPatio);
        tvNumLiquidacion = findViewById(R.id.tvNumLiquidacion);
        tvNumCertificado = findViewById(R.id.tvNumCertificado);
        tvNumOEntrada = findViewById(R.id.tvNumOEntrada);
        tvFechaActual = findViewById(R.id.tvFechaActual);

        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnCancelar);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        tvFechaActual.setText(currentDate);


        edtBuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                    edtBuscar.setText(edtBuscar.getText().toString().replaceAll("[^A-Z0-9]+", "").replaceAll("Q","").replaceAll("I","").replaceAll("Ñ","").replaceAll("O","").replaceAll(" ",""));

                    if (edtBuscar.getText().toString().length() > 17){
                        edtBuscar.setText(edtBuscar.getText().toString().substring(0,17));
                    }

                    if(edtBuscar.getText().toString().length() == 17){

                        getChasis("http://"+ VarGlobal.ConexionIP+"/"+VarGlobal.ConexionRute+"/consulta_Auto_Salida.php?ACHASIS="+edtBuscar.getText().toString());
                    }else {
                        Toast.makeText(SalidaAuto.this,"El Chasis no es Valido, Debe tener una longitud de 17 caracteres.",Toast.LENGTH_SHORT).show();
                        vaciar();
                    }


                }
                return false;
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaciar();
            }
        });

        btnCancelar.performClick();


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(SalidaAuto.this);
                builder1.setMessage("Le desea dar salida a este auto?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //usuario, fecha, chasis


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



                                darSalida("http://"+ VarGlobal.ConexionIP+"/"+VarGlobal.ConexionRute+"/confirmaAuto.php?id="+id_chasis+" &fecha="+fecha+"&hora="+hora+"&usuario="+VarGlobal.UserName+"&nombre_cliente="+salida.getNombre_cliente()+"&chasis="+salida.getChasis()+"&motor="+salida.getMotor()+"&descripcion="+salida.getDescripcion()+"&color="+salida.getColor()+"&patio"+salida.getPatio()+"&num_liquidacion"+salida.getNum_liquidacion()+"&num_certificado="+salida.getNum_certificado()+"&num_orden_entrada="+salida.getNum_orden_entrada());
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                btnCancelar.performClick();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


    }



    private void getChasis(String URL){
        salida = null;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            //El Response ya es un Array
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    try {
                            JSONObject jsonjObject = new JSONObject(response);
                            JSONArray jsonArray = jsonjObject.getJSONArray("ss");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectHijo = jsonArray.getJSONObject(i);
                                salida =  new ModeloSalida(
                                        jsonObjectHijo.getString("id"),
                                        jsonObjectHijo.getString("autoliquidarID"),
                                        jsonObjectHijo.getString("motor"),
                                        jsonObjectHijo.getString("num_certificado"),
                                        jsonObjectHijo.getString("num_cliente"),
                                        jsonObjectHijo.getString("nombre_cliente"),
                                        jsonObjectHijo.getString("chasis"),
                                        jsonObjectHijo.getString("descripcion"),
                                        jsonObjectHijo.getString("color"),
                                        jsonObjectHijo.getString("id_patio"),
                                        jsonObjectHijo.getString("patio"),
                                        jsonObjectHijo.getString("axco"),
                                        jsonObjectHijo.getString("modelo"),
                                        jsonObjectHijo.getString("num_liquidacion"),
                                        jsonObjectHijo.getString("num_orden_entrada"),
                                        jsonObjectHijo.getString("transmitador"));
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



try{
    id_chasis = salida.getId();
    tvChasis.setText(salida.getChasis());
    tvCliente.setText(salida.getNombre_cliente());
    tvMotor.setText(salida.getMotor());
    tvColor.setText(salida.getColor());
    tvDescripcion.setText(salida.getDescripcion());
    tvPatio.setText(salida.getPatio());
    tvNumLiquidacion.setText(salida.getNum_liquidacion());
    tvNumCertificado.setText(salida.getNum_certificado());
    tvNumOEntrada.setText(salida.getNum_orden_entrada());


}catch (Exception ex){
    vaciar();
    Toast.makeText(SalidaAuto.this,"No Disponible para Salida.", Toast.LENGTH_LONG).show();
}


                } else {
                    vaciar();
                    Toast.makeText(SalidaAuto.this,"El codigo es incorrecto.", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vaciar();
                Toast.makeText(SalidaAuto.this,error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("-", edtBuscar.getText().toString());


                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


private void darSalida(String URL){

    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if (!response.isEmpty()){
                Toast.makeText(SalidaAuto.this,"Salida Generada Correctamente.", Toast.LENGTH_LONG).show();
                vaciar();
            } else {
                Toast.makeText(SalidaAuto.this,"Error al Generar Salida", Toast.LENGTH_LONG).show();
                vaciar();

            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(SalidaAuto.this,"Error al actulizar.", Toast.LENGTH_LONG).show();
            vaciar();

        }
    }){
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

private void vaciar()
{
    edtBuscar.setText("");

    tvChasis.setText("");
    tvCliente.setText("");
    tvMotor.setText("");
    tvColor.setText("");
    tvDescripcion.setText("");
    tvPatio.setText("");
    tvNumLiquidacion.setText("");
    tvNumCertificado.setText("");
    tvNumOEntrada.setText("");
}

}
