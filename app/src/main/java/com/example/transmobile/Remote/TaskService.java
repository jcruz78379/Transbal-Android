package com.example.transmobile.Remote;

import com.example.transmobile.Salida.Modelos.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TaskService {

    @POST("ws/Salida/confirmaAuto.php")
    Call<Object> createTask(@Body Task task);



        //Call<Object> confirmarSalida(@Body String id);
   // void confirmarSalida(@Body Task task, Callback<Task> cb);

}
