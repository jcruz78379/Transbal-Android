package com.example.transmobile.Salida;

//import androidx.appcompat.app.AlertDialog;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.Home.Home;
import com.example.transmobile.R;
import com.example.transmobile.Remote.AuthService;
import com.example.transmobile.Remote.WebServiceJWT;
import com.example.transmobile.Salida.Adapter.salidaAdapter;
import com.example.transmobile.Salida.Modelos.Salida;
import com.example.transmobile.Utils.RecyclerTouchListener;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SalidaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private static List<Salida> salidaList  = new ArrayList<>();
    private salidaAdapter mAdapter;
    private RecyclerView  recyclerView;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida);

        toolbar = findViewById(R.id.toolbar);



        recyclerView = findViewById(R.id.recycler_view);

        SyncDownload();

        /*Salida s = new Salida();
        s.setColor("Color Rojo");
        s.setDescripcion("Description X");
        s.setChasis("Chasis X");
        s.setMotor("Motor X");
        salidaList.add(s);
        salidaList.add(s);*/

        mAdapter = new salidaAdapter(this, salidaList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);






        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {



                AlertDialog alertbox = new AlertDialog.Builder(view.getContext())
                        .setTitle("¿ESTÁ SEGURO?")
                        .setMessage("¿SEGURO DESEA REALIZAR LA SALIDA DEL CHASIS\n\n "+ salidaList.get(position).getChasis() + "?"+  salidaList.get(position).getId())
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                               /* startActivity(new Intent(V_MainActivity.this, V_Login.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();
*/

                                try {
                                    ConfirmarSalidaAuto(String.valueOf(salidaList.get(position).getId()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                Toast.makeText(getApplicationContext(),"el id editado es"+salidaList.get(position).getId(),Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                                Toast.makeText(getApplicationContext(),"NOOOO NI MERRGAS",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }




    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu,menu);

        final  MenuItem searchItem = menu.findItem(R.id.buscarAuto);
//        final SearchView searchView = MenuItemCompat.getActionProvider();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case   R.id.salir:

                Toast.makeText(this,"Has pulsado el Menú, Salir",Toast.LENGTH_LONG).show();
                break;

            case android.R.id.home:

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);

                    break;
        }

        return true;
    }

    // Metodo para Regresar al Home
    public  void click(View view){
        AlertDialog.Builder del = new AlertDialog.Builder(this);
        del.setTitle("ATENCIÓN");
        del.setMessage("Seguro desea Abandonar la Vista de Salida de Autos");
        del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
        del.setNegativeButton("No",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SalidaActivity.this,"Ha Cancelado la Acción",Toast.LENGTH_LONG).show();
            }
        });
        del.show();

    }

//    Metodo Listar Autos

    private void obtenerAutos(){
        String url = "";
    }


    public static void SyncDownload(){

        Call<Object> call = WebServiceJWT
                .getInstance()
                .createService(AuthService.class)
                .getSalidas();

      /*  AlertDialog.Builder builder = null;

        final AlertDialog progressDialog;

        if (builder == null) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("¡Descargando contenido de ordenes...!");
            final ProgressBar progressBar = new ProgressBar(context);
            LinearLayout.LayoutParams lp  = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            progressBar.setLayoutParams(lp);
            builder.setView(progressBar);
        }
        progressDialog = builder.create();
        progressDialog.show();*/

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {


                Object o  =  response.body();
                Object autos =  (Object)((LinkedTreeMap) o).get("consulta_auto");


                if(response.code() == 200 || response.isSuccessful()){




                    //sali = response.body();



                    for(int i=0; i<((ArrayList) autos).size(); i++) {

                        Salida salida = new Salida();

                        salida.setNombre_cliente( ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("nombre_cliente").toString() == null ? "-" : ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("nombre_cliente").toString() ) ;
                        salida.setChasis( ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("chasis").toString()                 == null ? "-" : ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("chasis").toString()) ;
                        salida.setMotor( ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("motor").toString()                   == null ? "-" : ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("motor").toString());
                        salida.setColor( ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("color").toString()                   == null ? "-" : ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("color").toString());
                        salida.setDescripcion( ((LinkedTreeMap) ((ArrayList) autos).get(i)).get("descripcion").toString()       == null ? "-" :((LinkedTreeMap) ((ArrayList) autos).get(i)).get("descripcion").toString() );
                        salida.setId( Integer.parseInt (((LinkedTreeMap) ((ArrayList) autos).get(i)).get("id").toString())      == 0 ? 0 :Integer.parseInt (((LinkedTreeMap) ((ArrayList) autos).get(i)).get("id").toString()));  // == null ? "-" :((LinkedTreeMap) ((ArrayList) autos).get(i)).get("id").toString() );

                        salidaList.add(salida);

                    }
                    //progressDialog.dismiss();
                    //toasty.primaryToasty(context, "¡Contenido de ordenes descargados correctamente!", Toasty.LENGTH_SHORT, Toasty.BOTTOM);

                }

                if (response.code()==404) {

                    Toast.makeText(context, "¡Error 404!",Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();

                }

                if (response.code()==500) {

                    Toast.makeText(context, "¡Error 500!",Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
              //  progressDialog.dismiss();
                //toasty.primaryToasty(context, "¡Error al descargar contenido de ordenes!", Toasty.LENGTH_SHORT, Toasty.BOTTOM);

                // Intent i = new Intent(context, V_Login.class);
               // context.startActivity(i);

            }
        });

    }


    public void ConfirmarSalidaAuto(String id) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VarGlobal.ConexionIP2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthService service = retrofit.create(AuthService.class);
        Call<Object> call = service.confirmarSalida(id);

        call.enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
       /* Response<Object> response = call.execute();
        response.isSuccessful();*/

    }



}
