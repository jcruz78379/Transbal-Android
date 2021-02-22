package com.example.transmobile.Reimpresion;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transmobile.Entradas.DeviceListActivity;
import com.example.transmobile.Entradas.Modelos.mCliente;
import com.example.transmobile.Entradas.Modelos.mModelo;
import com.example.transmobile.Entradas.Modelos.mPatio;
import com.example.transmobile.Entradas.RealmDB.rCliente;
import com.example.transmobile.Entradas.RealmDB.rPatio;
import com.example.transmobile.Entradas.UnicodeFormatter;
import com.example.transmobile.Entradas.VolleyConexion.VolleyConexion;
import com.example.transmobile.Global.VarGlobal;
import com.example.transmobile.R;
import com.example.transmobile.Reimpresion.Adapters.AdapterReimpresion;
import com.example.transmobile.Reimpresion.Modelos.mReimpresion;
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

public class ReimpresionActivity extends Activity implements Runnable {


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


    private Spinner spPatio, spClietne;
    private EditText edtChasis, etmPlaca;
    private CheckBox cbUsado;
    private Button btnGuardar;
    private ImageView imageView_print;
    private RecyclerView RecyclerViewInventario;
    private TextView tvFechaActual;

    private Context context;

    private int spPatioTouch = -1;
    private int spClienteTouch = -1;
    private int spModeloTouch = -1;

    private Timer timer;
    private SesionTimer sesionTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reimpresion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;


        Calibra = (Button) findViewById(R.id.Calibra);
        mDisc = (Button) findViewById(R.id.dis);
        mScan = (Button) findViewById(R.id.Scan);
        editText = (EditText) findViewById(R.id.editText);
        imageView_print = findViewById(R.id.imageView_print);

        spPatio = findViewById(R.id.sp_patio);
        spClietne = findViewById(R.id.spClietne);

        edtChasis = findViewById(R.id.edtChasis);
        cbUsado = findViewById(R.id.cbUsado);
        etmPlaca = findViewById(R.id.etmPlaca);
        tvFechaActual = findViewById(R.id.tvFechaActual);

        tvFechaActual.setText(GetCurrentTimeStamp());

        btnGuardar = findViewById(R.id.btnGuardar);
        RecyclerViewInventario = findViewById(R.id.RecyclerViewInventario);

        timer = new Timer();
        sesionTimer = new SesionTimer();
        timer.schedule(sesionTimer, 0, 60000);//una vez por minuto

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
                spPatioTouch++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final List<mModelo>[] ListModelo = new List[]{new ArrayList<mModelo>()};
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
                spClienteTouch++;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        edtChasis.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    edtChasis.setText(edtChasis.getText().toString().replaceAll("[^A-Z0-9]+", "").replaceAll("Q", "").replaceAll("I", "").replaceAll("Ñ", "").replaceAll("O", "").replaceAll(" ", ""));
                    if (edtChasis.getText().toString().length() > 17) {
                        edtChasis.setText(edtChasis.getText().toString().substring(0, 17));
                    }

                    if (edtChasis.getText().toString().length() != 17) {
                        Toast.makeText(context, "El Chasis no es Valido, Debe tener una longitud de 17 caracteres.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Activar_desactivar_campos(true);
                    }
                }
                return false;
            }
        });

        AdapterReimpresion _adapter = new AdapterReimpresion(context, rReimpresion.getAllReimpresion());
        LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(context);
        LinearLayoutManager.setReverseLayout(true);
        RecyclerViewInventario.setLayoutManager(LinearLayoutManager);
        RecyclerViewInventario.setAdapter(_adapter);
        RecyclerViewInventario.scrollToPosition(rReimpresion.getAllReimpresion().size() - 1);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!spPatio.getSelectedItem().equals("Seleccione")) {
                    if (!spClietne.getSelectedItem().equals("Seleccione")) {
                        if (edtChasis.getText().toString().length() == 17) {
                            if (cbUsado.isChecked() && etmPlaca.getText().toString().length() != 6) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Aviso!");
                                builder.setMessage("Debe colocar una placa valida.");
                                builder.setPositiveButton("Aceptar", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                boolean imprimir = false;
                                if (mBluetoothSocket != null)
                                    if (mBluetoothSocket.isConnected())
                                        imprimir = true;
                                if (imprimir) {
                                    String xyz = (etmPlaca.getText().toString().equals("")) ? edtChasis.getText().toString().substring(11, 17) : etmPlaca.getText().toString();
                                    editText.setText(ListCliente.get(Integer.parseInt(String.valueOf(spClietne.getSelectedItemId()))).getNombreClinete() +
                                            "|" + edtChasis.getText().toString() +
                                            "|" + xyz);
                                    mPrint.performClick();
                                    rReimpresion.addReimpresion(new mReimpresion(
                                            ListPatio.get(Integer.parseInt(String.valueOf(spPatio.getSelectedItemId()))).getId_Patio(),
                                            ListPatio.get(Integer.parseInt(String.valueOf(spPatio.getSelectedItemId()))).getNombrePatio(),
                                            ListCliente.get(Integer.parseInt(String.valueOf(spClietne.getSelectedItemId()))).getId_cliente(),
                                            ListCliente.get(Integer.parseInt(String.valueOf(spClietne.getSelectedItemId()))).getNombreClinete(), edtChasis.getText().toString(),
                                            cbUsado.isChecked() ? "Si" : "No",
                                            etmPlaca.getText().toString(),
                                            VarGlobal.NombreUsuario,
                                            GetCurrentTimeStamp2()
                                    ));
                                    AdapterReimpresion adapter = new AdapterReimpresion(context, rReimpresion.getAllReimpresion());
                                    LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(context);
                                    LinearLayoutManager.setReverseLayout(true);
                                    RecyclerViewInventario.setLayoutManager(LinearLayoutManager);
                                    RecyclerViewInventario.setAdapter(adapter);
                                    RecyclerViewInventario.scrollToPosition(rReimpresion.getAllReimpresion().size() - 1);

                                    limpiarpantalla();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Alerta!");
                                    builder.setMessage("Debe conectar una impresora a la PDT!");
                                    builder.setPositiveButton("Conectar una impresora", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            imageView_print.performClick();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Aviso!");
                            builder.setMessage("Debe colocar un chasis valido.");
                            builder.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
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

        imageView_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBluetoothSocket != null)
                    if (mBluetoothSocket.isConnected()) {
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Aviso!");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("Desea calibar o conectar la impresora?");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Conectar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mScan.performClick();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Calibrar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Calibra.performClick();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    } else {
                    }
                else {
                    mScan.performClick();
                }


            }
        });

        mDisc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Aviso!");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Desea desconetar la impresora");
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

                Thread t = new Thread() {
                    public void run() {
                        try {
                            OutputStream os = mBluetoothSocket.getOutputStream();
                            String BILL = "";

                            //BILL = "^XA^FO130,10^BY0,0.0,0^BQN,0,7^FD "+editText.getText().toString()+"^FS^CF0,30^FO130,170^FD"+editText.getText().toString()+"^FS^XZ";

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


            }
        });


        //msg= "~jc^xa^jus^xz";
        //                msg += "\n";
        mPrint = (Button) findViewById(R.id.mPrint);
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
                                        "^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
                                        "^XA\n" +
                                        "^MMT\n" +
                                        "^PW812\n" +
                                        "^LL0812\n" +
                                        "^LS0\n" +
                                        "^FT32,51^A0N,42,40^FH\\^FDTRANSBAL, S.A.^FS\n" +
                                        "^FT33,130^A0N,44,43^FH^CI28\\^FD" + data[0] + "^FS\n" +
                                        "^FT32,81^A0N,20,19^FH\\^FDCliente:^FS\n" +
                                        "^FT626,40^A0N,23,24^FH\\^FD" + GetCurrentTimeStamp() + "^FS\n" +
                                        "^FT568,39^A0N,20,19^FH\\^FDFecha:^FS\n" +
                                        "^FT82,355^A0N,248,220^FH\\^FD" + data[2] + "^FS\n" +
                                        "^FT618,784^BQN,2,6\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FT333,784^BQN,2,6\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FT32,784^BQN,2,6\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^BY3,4^FT513,107^B7N,4,0,,,N\n" +
                                        "^FH\\^FD" + data[1] + "^FS\n" +
                                        "^BY4,6^FT31,506^B7N,6,0,,,N\n" +
                                        "^FH\\^FD" + data[1] + "^FS\n" +
                                        "^FT581,609^BQN,2,8\n" +
                                        "^FH\\^FDMA," + data[1] + "^FS\n" +
                                        "^FT32,565^A0N,44,43^FH\\^FD" + data[1] + "^FS\n" +
                                        "^PQ1,0,1,Y^XZ\n";

                                // ETIQUETA 1

                                /*

                                 BILL = "\u0010CT~~CD,~CC^~CT~\n" +
                                        "^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
                                        "^XA\n" +
                                        "^MMT\n" +
                                        "^PW812\n" +
                                        "^LL0812\n" +
                                        "^LS0\n" +
                                        "^FT51,780^A0B,42,40^FH\\^FDTRANSBAL, S.A.^FS\n" +
                                        "^FT130,779^A0B,44,43^FH^CI28\\^FD"+data[0]+"^FS\n" +
                                        "^FT81,780^A0B,20,19^FH\\^FDCliente:^FS\n" +
                                        "^FT47,185^A0B,23,24^FH\\^FD"+GetCurrentTimeStamp()+"^FS\n" +
                                        "^FT46,243^A0B,20,19^FH\\^FDFecha:^FS\n" +
                                        "^FO597,10^GB0,793,8^FS\n" +
                                        "^FO355,5^GB0,793,8^FS\n" +
                                        "^FO143,7^GB0,793,8^FS\n" +
                                        "^FT328,730^A0B,223,220^FH\\^FD"+data[2]+"^FS\n" +
                                        "^FT640,212^BQN,2,6\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^FT640,497^BQN,2,6\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^FT640,798^BQN,2,6\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^BY4,6^FT481,780^B7B,6,0,,,N\n" +
                                        "^FH\\^FD"+data[1]+"^FS\n" +
                                        "^FT395,262^BQN,2,8\n" +
                                        "^FH\\^FDMA,"+data[1]+"^FS\n" +
                                        "^FT565,780^A0B,44,43^FH\\^FD"+data[1]+"^FS\n" +
                                        "^PQ1,0,1,Y^XZ";

                                 */

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

        imageView_print.performClick();
    }// fin oncreate

    private String GetCurrentTimeStamp() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.format(cal.getTime());
    }

    private String GetCurrentTimeStamp2() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date.format(cal.getTime());
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

    private void limpiarpantalla() {

        edtChasis.setText("");
        etmPlaca.setText("");
        cbUsado.isChecked();

    }

    public static Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    class SesionTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    VolleyConexion.setRemprecion(context, false);
                }
            });


        }
    }

}