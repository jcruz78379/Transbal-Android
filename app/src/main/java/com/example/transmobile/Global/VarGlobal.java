package com.example.transmobile.Global;

import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transmobile.Excepciones.Modelos.Excepcion;
import com.example.transmobile.Excepciones.Modelos.Imagen;
import com.example.transmobile.Salida.Modelos.ModeloMotivo;
import com.example.transmobile.Salida.Modelos.ModeloPatio;

import java.util.ArrayList;
import java.util.List;

public class VarGlobal {
    //131.108.6.12
    //192.168.1.141

    public static String Produccion = "131.108.6.12";
    public static String Test = "131.108.6.10";
    public static String Local = "192.168.114.242";
    public static String Home = "192.168.1.142";

    public static String URL = Home;


    public static String ipProduccion = "131.108.6.12";
    public static String ipPruebas1 = "131.108.6.12";
    public static String ipPruebas2 = "192.168.114.37";

    public static String ConexionIP = URL;
    public static String ConexionIP2 = URL;
    public static String ConexionRute = "ws/Salida";
    public static String ConexionRute2 = "ws/Inventario";
    public static String entradaUrl = "ws/Entrada";
    public static String reimpresionUrl = "ws/Reimpresion";
    public static String excepcionUrl = "ws/Excepcion";
    public static String transferenciaUrl = "ws/Transferencia";

    public static String _ConexionIP = "131.108.6.12";
    public static String _ConexionIP2 = "http://131.108.6.12/";
    public static String _ConexionRute = "ws/Salida";
    public static String _ConexionRute2 = "ws/Inventario";
    public static String _ConexionRute3 = "ws/InventarioAuto";


    public static String UserName = "";
    public static String NombreUsuario = "";

    public static String rutaRaiz = Environment.getExternalStorageDirectory() + "/";
    public static String DirImage =rutaRaiz +  "trasmovil";

    public final static String _rutaRaiz = Environment.getExternalStorageDirectory() + "/DBtrasmovil/";
    public final static String trasmovilDB = _rutaRaiz + "trasmovilDB.db";  //Archivo Name



     public static List<ModeloPatio> patioList = new ArrayList<ModeloPatio>();
     public static List<String> Listnamepatio = new ArrayList<String>();


    public static List<ModeloMotivo> MotivoList = new ArrayList<ModeloMotivo>();
    public static List<String> Listnamemotivo = new ArrayList<String>();




    public static  int por_revisar = 0;
    public static  int por_vencer = 0;
    public static  int vencidos = 0;

    public static  final int dias_por_vencer = 3;//cantidad de dias desde que sea alerta que esta por vencer
    public static  final int dias_vencer = 4;//cantidad de dias en el que se vence

    public static List<Imagen> ListnImagen = new ArrayList<Imagen>();
    public static List<Excepcion> ListnIExepciones = new ArrayList<Excepcion>();


    public static String Exepciones_cliente = "";
    public static String Exepciones_chasis = "";

    public static String ID_Excepcion = "";
    public static String NombreExcepcion = "";

    public static String Modelo = "";
    public static String Color = "";


    public static TextView numconteo;

    public static EditText editText;//entrada
    public static Button mPrint;//entrada


}
