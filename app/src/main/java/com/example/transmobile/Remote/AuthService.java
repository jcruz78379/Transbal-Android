package com.example.transmobile.Remote;

import com.example.transmobile.Salida.Modelos.Salida;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import com.example.transmobile.Salida.Modelos.Salida;
import com.example.transmobile.Salida.Modelos.Task;
import com.google.gson.JsonElement;

public interface AuthService {

    //Salida Handler
    @GET("WebService/Transbal/Salida_Auto/consulta_Auto.php")
    Call<Object> getSalidas();

    @FormUrlEncoded
    @POST("ws/Salida/confirmaAuto.php")
    Call<Object> confirmarSalida(@Field("id") String id);
    //Call<Task> createTask(@Body Task task);

}
