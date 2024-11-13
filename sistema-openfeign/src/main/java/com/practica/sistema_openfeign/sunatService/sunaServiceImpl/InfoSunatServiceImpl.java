package com.practica.sistema_openfeign.sunatService.sunaServiceImpl;

import com.practica.sistema_openfeign.aggregates.constants.Constants;
import com.practica.sistema_openfeign.aggregates.response.ResponseSunat;
import com.practica.sistema_openfeign.client.ClientSunat;
import com.practica.sistema_openfeign.redis.RedisService;
import com.practica.sistema_openfeign.sunatService.InfoSunatService;
import com.practica.sistema_openfeign.util.Util;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Log4j2
@Service
public class InfoSunatServiceImpl implements InfoSunatService {
    private final RedisService redisService;
    private final ClientSunat clientSunat;

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
            //Ejecutar clienteSunat OPEN FEIGN

            responseSunat = executionSunat(ruc);
            //validar la respuesta del cliente OpenFeign
            if (Objects.nonNull(responseSunat)){
                //Creo mi String para guardar en redis
                String dataParaRedis = Util.convertirAString(responseSunat);
                redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,
                        dataParaRedis,
                        Constants.REDIS_TTL);
            }
        }
        return  responseSunat;
    }

    //Metodo que ejecuta el client OpenFeign de SUNAT
    private ResponseSunat executionSunat(String ruc){
        String tokenOk = Constants.BEARER+ token;
        return clientSunat.getPersonaSunat(ruc,tokenOk);
    }

}
