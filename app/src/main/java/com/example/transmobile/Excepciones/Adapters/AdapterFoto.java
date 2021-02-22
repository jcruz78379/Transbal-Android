package com.example.transmobile.Excepciones.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmobile.Excepciones.FotosActivity;
import com.example.transmobile.Excepciones.Modelos.Excepcion;
import com.example.transmobile.Excepciones.Modelos.Imagen;
import com.example.transmobile.R;
import com.example.transmobile.Global.VarGlobal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdapterFoto extends RecyclerView.Adapter<AdapterFoto.HolderFoto>  {

    private Context context;
    private List<Imagen> List = new ArrayList<Imagen>();

    public AdapterFoto(Context context, java.util.List<Imagen> list) {
        this.context = context;
        List = list;
    }

    @NonNull
    @Override
    public HolderFoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new HolderFoto(LayoutInflater.from(context).inflate(R.layout.item_list_foto, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderFoto holder, final int position) {

        OutputStream outStream = null;
        Bitmap bitmap = null;

        File file = new File(List.get(position).getRuta());
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inPurgeable = true;
            bmOptions.inSampleSize = 0;
            bitmap = BitmapFactory.decodeFile(String.valueOf(file), bmOptions);
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            holder.foto.setImageBitmap(bitmap);
        } catch (java.lang.NullPointerException ex ){
            holder.item.setVisibility(View.GONE);
        }

        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog alertDialog;
                alertDialog = new Dialog(context);
                alertDialog.setCancelable(true);

                final View alertView = LayoutInflater.from(context).inflate(R.layout.view_foto, null, false);
                final ImageView imageView_foto = alertView.findViewById(R.id.imageView_foto);
                ImageView imageView_atras = alertView.findViewById(R.id.imageView_atras);
                ImageView imageView_borrar = alertView.findViewById(R.id.imageView_borrar);



                imageView_foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + List.get(position).getRuta()), "image/*");
                        context.startActivity(intent);
                    }
                });

                File mediaFile = new File(List.get(position).getRuta());
                Bitmap myBitmap = BitmapFactory.decodeFile(mediaFile.getAbsolutePath());
                int height = (myBitmap.getHeight());
                int width = (myBitmap.getWidth());
                final Bitmap scale = Bitmap.createScaledBitmap(myBitmap, width, height, true);
                imageView_foto.setImageBitmap(scale);

                imageView_atras.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                imageView_borrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:

                                        holder.item.setVisibility(View.GONE);

                                        VarGlobal.ListnImagen.remove(position);
                                        String listfotos = "";
                                        for(Imagen x : VarGlobal.ListnImagen){
                                            if(x.getID_Excepcion().equalsIgnoreCase(String.valueOf(VarGlobal.ID_Excepcion))){listfotos = listfotos + x.getName() + "," ;}
                                        }

                                        int count  = 0;
                                        for(Excepcion e :  VarGlobal.ListnIExepciones){
                                            if(VarGlobal.ListnIExepciones.get(position).getId().equalsIgnoreCase(String.valueOf(VarGlobal.ID_Excepcion))){
                                                VarGlobal.ListnIExepciones.get(count).setFotos(listfotos);
                                            }
                                            count++;
                                        }



                                        AdapterFoto adapterListImagenes = new AdapterFoto(context,VarGlobal.ListnImagen);
                                        FotosActivity.RVFotos.setLayoutManager(new GridLayoutManager(context, 1));
                                        FotosActivity.RVFotos.setAdapter(adapterListImagenes);


                                        new File(List.get(position).getRuta()).delete();
                                        Toast.makeText(context,"Borrado correctamente", Toast.LENGTH_LONG).show();
                                        alertDialog.dismiss();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("¿Desea borrar esta foto?").setPositiveButton("Si", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                });


                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(alertView);
                alertDialog.show();

            }
        });

        holder.textView2.setText(List.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class HolderFoto extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView textView2;
        LinearLayout item;
        public HolderFoto(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            foto = itemView.findViewById(R.id.imageViewFoto);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
