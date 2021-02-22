package com.example.transmobile.Entradas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmobile.Entradas.Adapters.AdapterReimprecionEntrada;
import com.example.transmobile.Entradas.Modelos.mCliente;
import com.example.transmobile.Entradas.Modelos.mColor;
import com.example.transmobile.Entradas.Modelos.mEntrada;
import com.example.transmobile.Entradas.Modelos.mModelo;
import com.example.transmobile.Entradas.Modelos.mPatio;
import com.example.transmobile.Entradas.RealmDB.rCliente;
import com.example.transmobile.Entradas.RealmDB.rColor;
import com.example.transmobile.Entradas.RealmDB.rEntrada;
import com.example.transmobile.Entradas.RealmDB.rPatio;
import com.example.transmobile.Entradas.VolleyConexion.VolleyConexion;
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.R;
import com.example.transmobile.Reimpresion.Adapters.AdapterReimpresion;
import com.example.transmobile.Reimpresion.RealmDB.rReimpresion;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static com.example.transmobile.Entradas.RealmDB.rModelo.getModeloByCliente;


public class EntradaActivity extends Activity implements Runnable {


    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc, Calibra;
    EditText editText;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    Context context;
    String modeloImpresora = "";
    final String[] print_modelos = new String[2];


    private Spinner spClietne, spModelo, spColor, spGasolina, spPatio;
    private EditText edtChasis, edtLicencia, etmObservacion, edtPatio, edtCedula, edtNombreConductor, edtKilometraje;
    private CheckBox cbObservacion;
    private Button btnConfirmar2, btnCancelar;
    private ImageView imageView_print, imageView_reprint;


    private Timer timer;
    private SesionTimer sesionTimer;

    @Override
    public void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.activity_entrada2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;

        Calibra = (Button) findViewById(R.id.Calibra);
        mDisc = (Button) findViewById(R.id.dis);
        mScan = (Button) findViewById(R.id.Scan);
        mPrint = (Button) findViewById(R.id.mPrint);
        editText = (EditText) findViewById(R.id.editText);
        imageView_print = findViewById(R.id.imageView_print);
        imageView_reprint = findViewById(R.id.imageView_reprint);




        spPatio = findViewById(R.id.spPatio);
        spClietne = findViewById(R.id.spClietne);
        spModelo = findViewById(R.id.spModelo);
        spColor = findViewById(R.id.spColor);
        spGasolina = findViewById(R.id.spGasolina);
        edtChasis = findViewById(R.id.edtChasis);
        edtLicencia = findViewById(R.id.edtLicencia);
        etmObservacion = findViewById(R.id.etmPlaca);

        cbObservacion = findViewById(R.id.cbUsado);
        edtCedula = findViewById(R.id.edtCedula);
        edtNombreConductor = findViewById(R.id.edtNombreConductor);
        edtKilometraje = findViewById(R.id.edtKilometraje);
        btnConfirmar2 = findViewById(R.id.btnConfirmar2);
        btnCancelar = findViewById(R.id.btnCancelar);


        Activar_desactivar_campos(false);


        timer = new Timer();
        sesionTimer = new SesionTimer();
        timer.schedule(sesionTimer, 0, 60000);//una vez por minuto


        edtChasis.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                    edtChasis.setText(edtChasis.getText().toString().replaceAll("[^A-Z0-9]+", "").replaceAll("Q", "").replaceAll("I", "").replaceAll("Ñ", "").replaceAll("O", "").replaceAll(" ", ""));

                    if (edtChasis.getText().toString().length() > 17) {
                        edtChasis.setText(edtChasis.getText().toString().substring(0, 17));
                        edtLicencia.requestFocus();
                    }

                    if (edtChasis.getText().toString().length() != 17) {
                        Toast.makeText(context, "El Chasis no es Valido, Debe tener una longitud de 17 caracteres.", Toast.LENGTH_SHORT).show();

                    } else {
                        Activar_desactivar_campos(true);
                    }
                }
                return false;
            }
        });


        edtLicencia.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                    if (edtLicencia.getText().toString().contains("|")) {
                        try {
                            String[] value_split = edtLicencia.getText().toString().split("\\|");
                            edtCedula.setText(value_split[0]);
                            edtLicencia.setText(value_split[1]);
                            edtNombreConductor.setText(value_split[2].replaceAll("_", " ") + " " + value_split[3].replaceAll("_", " "));
                            edtKilometraje.requestFocus();
                        } catch (Exception ex) {
                            edtLicencia.setText("");
                            edtCedula.setText("");
                            edtNombreConductor.setText("");
                        }
                    }

                }
                return false;
            }
        });
        /*
        edtChasis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                edtLicencia.requestFocus();
            }
        });
        edtLicencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                edtKilometraje.requestFocus();
            }
        });
/*/


        cbObservacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                etmObservacion.setEnabled(b);
            }
        });


        final List<mModelo>[] ListModelo = new List[]{new ArrayList<mModelo>()};


        final List<mPatio> ListPatio = rPatio.getAllPatio();
        final List<String> ListStrPatio = new ArrayList<String>();
        for (mPatio x : ListPatio) {
            ListStrPatio.add(x.getNombrePatio());
        }
        ArrayAdapter<String> adapterPatio = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ListStrPatio);
        spPatio.setAdapter(adapterPatio);
        spPatio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        final List<mCliente> ListCliente = rCliente.getAllCliente();
        final List<String> ListStrCliente = new ArrayList<String>();
        for (mCliente x : ListCliente) {
            ListStrCliente.add(x.getNombreClinete());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ListStrCliente);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClietne.setAdapter(adapter);
        spClietne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ListModelo[0] = (List<mModelo>) getModeloByCliente(ListCliente.get(i).getId_cliente());
                List<String> ListStrModelo = new ArrayList<String>();
                for (mModelo x : ListModelo[0]) {
                    ListStrModelo.add(x.getNombreModelo());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ListStrModelo);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spModelo.setAdapter(adapter);
                spModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        final List<mColor> ListColor = rColor.getAllColor();
        final List<String> ListStrColor = new ArrayList<String>();
        for (mColor x : ListColor) {
            ListStrColor.add(x.getNombreColor());
        }
        ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ListStrColor);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(adapterColor);
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final List<String> ListStrGasolina = new ArrayList<String>();
        ListStrGasolina.add("Seleccione");
        ListStrGasolina.add("1/4 Tanque");
        ListStrGasolina.add("1/2 Tanque");
        ListStrGasolina.add("3/4 Tanque");
        ListStrGasolina.add("Full");
        ListStrGasolina.add("Vacio");
        ArrayAdapter<String> adapterGasolina = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ListStrGasolina);
        adapterGasolina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGasolina.setAdapter(adapterGasolina);
        spGasolina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarpantalla();
            }
        });

        btnConfirmar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!spPatio.getSelectedItem().equals("Seleccione")) {
                    if (!spClietne.getSelectedItem().equals("Seleccione")) {
                        if (!spModelo.getSelectedItem().equals("Seleccione")) {
                            if (!spColor.getSelectedItem().equals("Seleccione")) {
                                if (!spGasolina.getSelectedItem().equals("Seleccione")) {
                                    if (edtChasis.getText().toString().length() == 17) {
                                        if (edtCedula.getText().toString().equalsIgnoreCase("") || edtKilometraje.getText().toString().equalsIgnoreCase("")) {
                                            Toast.makeText(context, "Debe llenar todos los campos correctamente", Toast.LENGTH_LONG).show();

                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setTitle("Debe llenar todos los campos correctamente!");
                                            builder.setMessage("Recuerde que los campos CHASIS, CEDULA, CHOFER y  KILOMETRAJE son campos OBLIGATORIOS por lo que deben ser llenados correctamente.");
                                            builder.setPositiveButton("Aceptar", null);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();


                                        } else {
                                            String _modelo = "";
                                            try {
                                                _modelo = ListModelo[0].get(Integer.parseInt(String.valueOf(spModelo.getSelectedItemId()))).getNombreModelo();
                                            } catch (Exception ex) {
                                            }

                                            boolean imprimir = false;

                                            if (mBluetoothSocket != null)
                                                imprimir = mBluetoothSocket.isConnected();

                                            if (rEntrada.addEntrada(new mEntrada(
                                                    ListCliente.get(Integer.parseInt(String.valueOf(spClietne.getSelectedItemId()))).getNombreClinete(),
                                                    edtChasis.getText().toString(),
                                                    edtLicencia.getText().toString(),
                                                    edtCedula.getText().toString(),
                                                    edtNombreConductor.getText().toString(),
                                                    VarGlobal.UserName,
                                                    GetCurrentTimeStamp(),
                                                    etmObservacion.getText().toString(),
                                                    ListPatio.get(Integer.parseInt(String.valueOf(spPatio.getSelectedItemId()))).getNombrePatio(),
                                                    spGasolina.getSelectedItem().toString(),
                                                    edtKilometraje.getText().toString(),
                                                    _modelo,
                                                    ListColor.get(Integer.parseInt(String.valueOf(spColor.getSelectedItemId()))).getNombreColor(),
                                                    (imprimir) ? "Si" : "No"))) {
                                                Toast.makeText(context, "Entrada guardada.", Toast.LENGTH_LONG).show();


                                                editText.setText(ListCliente.get(Integer.parseInt(String.valueOf(spClietne.getSelectedItemId()))).getNombreClinete() +
                                                        "|" + edtChasis.getText().toString() +
                                                        "|" + edtChasis.getText().toString().substring(11, 17) +
                                                        "|" + GetCurrentTimeStamp2());
                                                mPrint.performClick();


                                            } else
                                                Toast.makeText(context, "Error al guardar.", Toast.LENGTH_LONG).show();
                                            limpiarpantalla();
                                            Activar_desactivar_campos(false);
                                        }
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Chasis no valido!");
                                        builder.setMessage("Debe llenar el chasis correctamente.");
                                        builder.setPositiveButton("Aceptar", null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        Toast.makeText(context, "Debe seleccionar todas las opciones.", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Aviso!");
                                    builder.setMessage("Verifique que el Gasolina este correcto.");
                                    builder.setPositiveButton("Aceptar", null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    Toast.makeText(context, "Debe seleccionar todas las opciones.", Toast.LENGTH_LONG).show();

                                }

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Aviso!");
                                builder.setMessage("Verifique que el Color este correcto.");
                                builder.setPositiveButton("Aceptar", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Toast.makeText(context, "Debe seleccionar todas las opciones.", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Aviso!");
                            builder.setMessage("Verifique que el Modelo este correcto.");
                            builder.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            Toast.makeText(context, "Debe seleccionar todas las opciones.", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Aviso!");
                        builder.setMessage("Verifique que el Cliente este correcto.");
                        builder.setPositiveButton("Aceptar", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Toast.makeText(context, "Debe seleccionar todas las opciones.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Aviso!");
                    builder.setMessage("Verifique que el PATIO este correcto.");
                    builder.setPositiveButton("Aceptar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Toast.makeText(context, "Debe seleccionar todas las opciones.", Toast.LENGTH_LONG).show();
                }


            }
        });


/**======================================Imprecion==============================================**/


        mScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(context, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent,
                                REQUEST_ENABLE_BT);
                    } else {
                        ListPairedDevices();
                        Intent connectIntent = new Intent(context,
                                DeviceListActivity.class);
                        startActivityForResult(connectIntent,
                                REQUEST_CONNECT_DEVICE);
                    }
                }
            }
        });


        imageView_reprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Aviso!");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Desea Reimprimir una entrada?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Dialog alertDialog;
                                alertDialog = new Dialog(context);
                                alertDialog.setCancelable(true);

                                View alertView = LayoutInflater.from(context).inflate(R.layout.dialog_list_reprint, null, false);
                                final RecyclerView RvEntradasReprint = alertView.findViewById(R.id.RvEntradasReprint);
                                final TextView textViewCancelar = alertView.findViewById(R.id.textViewCancelar);


                                AdapterReimprecionEntrada _adapter = new AdapterReimprecionEntrada(context, rEntrada.getAllEntrada());
                                LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(context);
                                LinearLayoutManager.setReverseLayout(true);
                                RvEntradasReprint.setLayoutManager(LinearLayoutManager);
                                RvEntradasReprint.setAdapter(_adapter);

                                textViewCancelar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                alertDialog.setContentView(alertView);
                                alertDialog.show();


                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        imageView_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScan.performClick();
            }
        });

        mDisc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Aviso!");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Desea desconetar la impresora?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mBluetoothAdapter != null) {
                                    mBluetoothAdapter.disable();
                                    mDisc.setVisibility(View.GONE);
                                    mScan.setVisibility(View.VISIBLE);
                                }
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });
        //


        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_TAB)) {
                    mPrint.performClick();
                }
                return false;
            }
        });


        Calibra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Aviso!");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Desea calibar la impresora?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Thread t = new Thread() {
                                    public void run() {
                                        try {
                                            OutputStream os = mBluetoothSocket.getOutputStream();
                                            String BILL = "";

                                            //BILL = "^XA^FO130,10^BY0,0.0,0^BQN,0,7^FD "+editText.getText().toString()+"^FS^CF0,30^FO130,170^FD"+editText.getText().toString()+"^FS^XZ";

                                            //BILL = "~jc^xa^jus^xz" + "\n";//codigo para calibrar

                                            BILL = "! U1 SPEED 5" + "\n" + "! U1 setvar \"print.tone\" \"0\"" + "\n" + "! U1 setvar \"media.type\" \"label\"" + "\n" + "! U1 setvar \"media.sense_mode\" \"gap\"" + "\n" + "~jc^xa^jus^xz" + "\n";//codigo para calibrar
                                            os.write(BILL.getBytes());
                                            //This is printer specific code you can comment ==== > Start

                                            // Setting height
                                            int gs = 29;
                                            os.write(intToByteArray(gs));
                                            int h = 104;
                                            os.write(intToByteArray(h));
                                            int n = 162;
                                            os.write(intToByteArray(n));

                                            // Setting Width
                                            int gs_width = 100;
                                            os.write(intToByteArray(gs_width));
                                            int w = 700;
                                            os.write(intToByteArray(w));
                                            int n_width = 2;
                                            os.write(intToByteArray(n_width));

                                            editText.setText("");
                                        } catch (Exception e) {
                                            Log.e("MainActivity", "Exe ", e);
                                        }
                                    }
                                };
                                t.start();
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


            }
        });


        //msg= "~jc^xa^jus^xz";
        //                msg += "\n";

        mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                final String texto = editText.getText().toString();


                if (texto.length() < 1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Debe colocar un texto.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    editText.setText("");
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Thread t = new Thread() {
                        public void run() {
                            try {

                                OutputStream os = mBluetoothSocket.getOutputStream();
                                String[] data = texto.split("\\|");
                                String BILL = "";
                                BILL = "\u0010CT~~CD,~CC^~CT~\n" +
                                        "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
                                        "^XA\n" +
                                        "^MMT\n" +
                                        "^PW812\n" +
                                        "^LL0812\n" +
                                        "^LS0\n" +
                                        "^FT11,60^A0N,44,43^FH\\^FDTRANSBAL, S.A.^FS\n" +
                                        "^FT13,96^A0N,20,19^FH^CI28\\^FDCliente:^FS\n" +
                                        "^FT597,768^A0B,10,9^FH^CI28\\^FD" + data[0] + "^FS\n" +
                                        "^FT335,772^A0B,10,9^FH^CI28\\^FD" + data[0] + "^FS\n" +
                                        "^FT61,769^A0B,10,9^FH^CI28\\^FD" + data[0] + "^FS\n" +
                                        "^FT12,149^A0N,44,43^FH^CI28\\^FD" + data[0] + "^FS\n" +
                                        "^FT531,37^A0N,20,19^FH\\^FDFecha:^FS\n" +
                                        "^FT605,38^A0N,23,24^FH\\^FD" + data[3] + "^FS\n" +
                                        "^BY5,5^FT29,602^B7N,5,0,,,N\n" +
                                        "^FH\\^FD" + data[1] + "^FS\n" +
                                        "^BY4,2^FT453,83^B7N,2,0,,,N\n" +
                                        "^FH\\^FD" + data[1] + "^FS\n" +
                                        "^FT609,800^BQN,2,7\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FT353,805^BQN,2,7\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FT82,807^BQN,2,7\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FT573,630^BQN,2,8\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FT6,430^A0N,354,271^FH\\^FD" + data[2] + "^FS\n" +
                                        "^FT32,508^A0N,40,48^FH\\^FD" + data[1] + "^FS\n" +
                                        "^PQ1,0,1,Y^XZ\n";

                                // ETIQUETA 2
                              /*  BILL = "\u0010CT~~CD,~CC^~CT~\n" +
                                        "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
                                        "^XA\n" +
                                        "^MMT\n" +
                                        "^PW812\n" +
                                        "^LL0812\n" +
                                        "^LS0\n" +
                                        "^FT58,798^A0B,44,43^FH\\^FDTRANSBAL, S.A.^FS\n" +
                                        "^FT96,799^A0B,20,19^FH\\^FDCliente:^FS\n" +
                                        "^FT773,186^A0I,10,9^FH^CI28\\^FD"+data[0]+"^FS\n" +
                                        "^FT772,477^A0I,10,9^FH^CI28\\^FD"+data[0]+"^FS\n" +
                                        "^FT772,763^A0I,10,9^FH^CI28\\^FD"+data[0]+"^FS\n" +
                                        "^FT149,800^A0B,44,43^FH^CI28\\^FD"+data[0]+"^FS\n" +
                                        "^FT34,278^A0B,20,19^FH\\^FDFecha:^FS\n" +
                                        "^FT35,204^A0B,23,24^FH\\^FD"+data[3]+"^FS\n" +
                                        "^FT660,194^BQN,2,6\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^BY5,6^FT627,785^B7B,6,0,,,N\n" +
                                        "^FH\\^FD"+data[1]+"^FS\n" +
                                        "^FT660,484^BQN,2,6\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^BY4,4^FT107,359^B7B,4,0,,,N\n" +
                                        "^FH\\^FD"+data[1]+"^FS\n" +
                                        "^FT660,771^BQN,2,6\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^BY2,3,64^FT525,782^BAB,,N,N\n" +
                                        "^FD"+data[1]+"^FS\n" +
                                        "^FT456,242^BQN,2,8\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^FT408,806^A0B,321,271^FH\\^FD"+data[2]+"^FS\n" +
                                        "^PQ1,0,1,Y^XZ"; */

                                //    ETIQUETA 1
                               /* BILL = "\u0010CT~~CD,~CC^~CT~" +
                                        "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
                                        "^XA\n" +
                                        "^MMT\n" +
                                        "^PW812\n" +
                                        "^LL0812\n" +
                                        "^LS0\n" +
                                        "^FT14,58^A0N,44,43^FH\\^FDTRANSBAL, S.A.^FS\n" +
                                        "^FT13,96^A0N,20,19^FH\\^FDCliente:^FS\n" +
                                        "^FT626,773^A0B,10,9^FH\\^FD" + data[0] + "^FS\n" +
                                        "^FT335,772^A0B,10,9^FH\\^FD" + data[0] + "^FS\n" +
                                        "^FT49,772^A0B,10,9^FH\\^FD" + data[0] + "^FS\n" +
                                        "^FT12,149^A0N,44,43^FH\\^FD" + data[0] + "^FS\n" +
                                        "^FT534,34^A0N,20,19^FH\\^FDFecha:^FS\n" +
                                        "^FT608,35^A0N,23,24^FH\\^FD" + data[3] + "^FS\n" +
                                        "^FT637,805^BQN,2,6\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^BY5,7^FT15,391^B7N,7,0,,,N\n" +
                                        "^FH\\^FD" + data[1] + "^FS\n" +
                                        "^FT346,804^BQN,2,6\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^BY4,3^FT453,95^B7N,3,0,,,N\n" +
                                        "^FH\\^FD" + data[1] + "^FS\n" +
                                        "^FT59,804^BQN,2,6\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^BY2,3,93^FT17,272^BAN,,N,N\n" +
                                        "^FD" + data[1] + "^FS\n" +
                                        "^FT576,405^BQN,2,10\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FO2,405^GB805,0,8^FS\n" +
                                        "^FO1,612^GB805,0,8^FS\n" +
                                        "^FO4,162^GB805,0,8^FS\n" +
                                        "^FT9,599^A0N,238,271^FH\\^FD" + data[2] + "^FS\n" +
                                        "^PQ1,0,1,Y^XZ";  */


                                os.write(BILL.getBytes());
                                //This is printer specific code you can comment ==== > Start

                                // Setting height
                                int gs = 29;
                                os.write(intToByteArray(gs));
                                int h = 104;
                                os.write(intToByteArray(h));
                                int n = 162;
                                os.write(intToByteArray(n));

                                // Setting Width
                                int gs_width = 100;
                                os.write(intToByteArray(gs_width));
                                int w = 700;
                                os.write(intToByteArray(w));
                                int n_width = 2;
                                os.write(intToByteArray(n_width));

                                editText.setText("");
                            } catch (Exception e) {
                                Log.e("MainActivity", "Exe ", e);
                            }
                        }
                    };
                    t.start();
                }
            }
        });

        VarGlobal.editText = editText;
        VarGlobal.mPrint = mPrint;

        imageView_print.performClick();
    }// onCreate


    private String GetCurrentTimeStamp() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        return date.format(cal.getTime());
    }

    public static String GetCurrentTimeStamp2() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.format(cal.getTime());
    }

    private void Activar_desactivar_campos(boolean a) {

        spGasolina.setEnabled(a);
        edtCedula.setEnabled(a);
        edtNombreConductor.setEnabled(a);
        edtKilometraje.setEnabled(a);
        edtLicencia.setEnabled(a);
        cbObservacion.setEnabled(a);
        btnConfirmar2.setEnabled(a);

        if (a && cbObservacion.isChecked()) {
            etmObservacion.setEnabled(a);
        } else {
            etmObservacion.setEnabled(false);
        }
    }

    private void limpiarpantalla() {
        edtChasis.setText("");
        edtCedula.setText("");
        edtNombreConductor.setText("");
        edtLicencia.setText("");
        edtKilometraje.setText("");
        etmObservacion.setText("");
        cbObservacion.setChecked(false);
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(context,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(context, "¡Impresora Conectada correcatamente!", Toast.LENGTH_SHORT).show();
            mScan.setVisibility(View.GONE);
            mDisc.setVisibility(View.VISIBLE);
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }

    class SesionTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    VolleyConexion.setEntrada2(context);
                }
            });


        }
    }
}
