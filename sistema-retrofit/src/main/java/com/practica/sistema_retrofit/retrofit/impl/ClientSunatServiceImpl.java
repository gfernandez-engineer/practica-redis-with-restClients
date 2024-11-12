package com.practica.sistema_retrofit.retrofit.impl;

import com.practica.sistema_retrofit.aggregates.constants.Constants;
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Log4j2
public class ClientSunatServiceImpl {
    private static Retrofit retrofit = null;

    public static  Retrofit getRetrofitSunat(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            log.info("ClienteSunatServiceImpl -> CREANDO CLIENTE RETROFIT CON URL Y PARAMETROS(SIN VALORES)");
        }
        return retrofit;
    }
}
