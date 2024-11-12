package com.practica.sistema_retrofit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practica.sistema_retrofit.aggregates.constants.Constants;
import com.practica.sistema_retrofit.redis.RedisService;
import com.practica.sistema_retrofit.response.ResponseSunat;
import com.practica.sistema_retrofit.retrofit.ClientSunatService;
import com.practica.sistema_retrofit.retrofit.impl.ClientSunatServiceImpl;
import com.practica.sistema_retrofit.service.InfoSunatService;
import com.practica.sistema_retrofit.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Log4j2
@Service
public class InfoSunatServiceImpl implements InfoSunatService {
    private final RedisService redisService;

    //Definición de una instancia de retrofit que se pueda usar
    ClientSunatService clientSunatService = ClientSunatServiceImpl
            .getRetrofitSunat()
            .create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;

    @Override
    public ResponseSunat getInfoSunat(String ruc) throws IOException {
        ResponseSunat responseSunat = new ResponseSunat();
        //Logica de mi diagrama
        //Recupero la información de Redis
        String sunatRedisInfo = redisService.getDataFromRedis(
                Constants.REDIS_KEY_API_SUNAT+ruc);
        //Validando que exista info o no
        if (Objects.nonNull(sunatRedisInfo)){
            //Si existe info en Redis
            responseSunat = Util.convertirDesdeString(sunatRedisInfo,ResponseSunat.class);
        }else {
            //No existe info en redis, iremos al cliente Sunat
            //Ejecutar clienteSunat Retrofit
            Response<ResponseSunat> executeSunat = preparacionClientSunat(ruc).execute();
            //validar que responde el API
            if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
                //Recupero el body (Solo el cuerpo porque alli tengo la información que requiero)
                responseSunat = executeSunat.body();
                //Creo mi String para guardar en redis
                String dataParaRedis = Util.convertirAString(responseSunat);
                redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,
                        dataParaRedis,
                        Constants.REDIS_TTL);
            }
        }
        return  responseSunat;
    }

    private Call<ResponseSunat> preparacionClientSunat(String ruc){
        log.info("prepareSunatRetrofit -> Ejecutando Metodo de Apoyo que crea el objeto retrofit completo\"");
        return clientSunatService.getInfoSunat(Constants.BEARER+token,ruc);
    }

}
