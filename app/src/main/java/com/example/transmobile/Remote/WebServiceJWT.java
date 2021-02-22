package com.example.transmobile.Remote;

import com.example.transmobile.Global.VarGlobal;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceJWT {

    private static WebServiceJWT instance;
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;

    private WebServiceJWT(){
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(VarGlobal.ConexionIP2) //BASE_URL_JWT //Credentials.URL
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized WebServiceJWT getInstance(){
        if(instance == null){
            instance = new WebServiceJWT();
        }
        return instance;
    }

    public <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }


}
