package com.example.transmobile.Excepciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transmobile.Excepciones.Adapters.AdapterFoto;
import com.example.transmobile.Excepciones.Modelos.Imagen;
import com.example.transmobile.R;
import com.example.transmobile.Global.VarGlobal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotosActivity extends AppCompatActivity {
    private Context context = null;
    private static int TAKE_PICTURE = 1;
    private String name = "";
    private String NameSimple = "";

    private Button buttonTomarFoto = null;
    private TextView textViewClinete = null;
    private TextView textViewChasis = null;

    public static RecyclerView RVFotos = null;

    private String ID_Excepcion = "";
    private String NombreExcepcion = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        // Ocultamos La barra de acción


        context = this;
        createFolder();

        ID_Excepcion = VarGlobal.ID_Excepcion;
        NombreExcepcion =  VarGlobal.NombreExcepcion;
        textViewClinete = findViewById(R.id.textViewClinete);
        textViewChasis = findViewById(R.id.textViewChasis);
        textViewChasis.setText(VarGlobal.Exepciones_chasis);
        textViewClinete.setText(VarGlobal.Exepciones_cliente);

        buttonTomarFoto = findViewById(R.id.buttonTomarFoto);
        buttonTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto();
            }
        });

        RVFotos = findViewById(R.id.RVFotos);
        AdapterFoto adapterListImagenes = new AdapterFoto(context,VarGlobal.ListnImagen);
        RVFotos.setLayoutManager(new GridLayoutManager(context, 1));
        RVFotos.setAdapter(adapterListImagenes);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == TAKE_PICTURE) {
                new MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null;
                    {
                        msc = new MediaScannerConnection(getApplicationContext(), this);
                        msc.connect();
                    }
                    public void onMediaScannerConnected() {
                        msc.scanFile(name, null);
                    }

                    public void onScanCompleted(String path, Uri uri) {
                        msc.disconnect();
                    }
                };
                VarGlobal.ListnImagen.add(new Imagen(NameSimple,name,String.valueOf(ID_Excepcion),true));



                AdapterFoto adapterListImagenes = new AdapterFoto(context,VarGlobal.ListnImagen);
                RVFotos.setLayoutManager(new GridLayoutManager(context, 1));
                RVFotos.setAdapter(adapterListImagenes);
                RVFotos.scrollToPosition(VarGlobal.ListnImagen.size() - 1);






            }
        } catch (Exception ex) {

            Toast.makeText(context, "Archivo no seleccioando.", Toast.LENGTH_LONG).show();

        }
    }


    private void TomarFoto(){
       String Direccion = VarGlobal.DirImage ;
       NameSimple = Name_Pic();
       name = Direccion + NameSimple;



        try {
            File imagen = new File(name);

            if (imagen.createNewFile()) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri output = Uri.fromFile(new File(name));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, TAKE_PICTURE);

            } else {
                Toast.makeText(getApplicationContext(), "Error al crear el archivo.", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error al crear el archivo.\n" + e, Toast.LENGTH_SHORT).show();
            textViewChasis.setText(e.toString());
        }



    }

    private void createFolder() {

        if (shouldAskPermissions()) {
            askPermissions();
        } else {

            File rutaRaiz = new File(VarGlobal.DirImage);
            if (!rutaRaiz.exists()) {
                rutaRaiz.mkdir();
                if (rutaRaiz.exists()) {
                    Toast.makeText(context, "Carpeta raiz creada", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Error al crear la carpeta raiz", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.media.action.IMAGE_CAPTURE",
                "android.permission.CAMERA",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_INTERNAL_STORAGE",
                "android.permission.WRITE_INTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @SuppressLint("SimpleDateFormat")
    private String Name_Pic() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        String photoCode = "pic_"+VarGlobal.Exepciones_chasis+"_"+ NombreExcepcion +"_"+ date + "_" + VarGlobal.UserName + ".jpg";
        return photoCode;
    }


    public Bitmap ReducirPeso(String direccion, String Nombre) {

        String filename = Nombre;
        String extStorageDirectory = direccion;

        OutputStream outStream = null;
        Bitmap bitmap = null;

        File file = new File(extStorageDirectory + filename);
        if (file.exists()) {
            int m_inSampleSize = 0;
            int m_compress = 50;
            try {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inPurgeable = true;
                bmOptions.inSampleSize = m_inSampleSize;
                bitmap = BitmapFactory.decodeFile(String.valueOf(file), bmOptions);
                outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, m_compress, outStream);
                outStream.flush();
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public Bitmap RotarImagen(String Direccion, String Nombre, int rotacion) {
        File mediaFile = new File(VarGlobal.DirImage + Nombre);
        Bitmap rotateBitmap = null;
        ExifInterface exif = null;
        if (mediaFile.exists()) {
            if (isImage(Direccion + Nombre)) {
                Bitmap myBitmap = BitmapFactory.decodeFile(mediaFile.getAbsolutePath());
                int height = (myBitmap.getHeight());
                int width = (myBitmap.getWidth());
                Bitmap scale = Bitmap.createScaledBitmap(myBitmap, width, height, true);
                int rotate = 0;
                try {
                    exif = new ExifInterface(mediaFile.getAbsolutePath());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_NORMAL:
                        rotate = 0;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotacion);
                rotateBitmap = Bitmap.createBitmap(scale, 0, 0, scale.getWidth(),
                        scale.getHeight(), matrix, true);

                save(rotateBitmap, Direccion, Nombre);
                mediaFile.delete();
            }
        }
        return rotateBitmap;
    }

    public String save(Bitmap bitmapImage, String direccion, String Nombre) {

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(direccion + Nombre);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Nombre;
    }

    public static boolean isImage(String str) {
        boolean temp = false;
        String[] arr = {".jpeg", ".jpg", ".png", ".bmp", ".gif"};
        for (int i = 0; i < arr.length; i++) {
            temp = str.endsWith(arr[i]);
            if (temp) {
                break;
            }
        }
        return temp;
    }


}