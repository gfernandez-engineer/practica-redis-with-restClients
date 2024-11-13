package com.practica.sistema_resttemplate.sunatService.sunatServiceImpl;

import com.practica.sistema_resttemplate.aggregates.constants.Constants;
import com.practica.sistema_resttemplate.aggregates.response.ResponseSunat;
import com.practica.sistema_resttemplate.redis.RedisService;
import com.practica.sistema_resttemplate.sunatService.InfoSunatService;
import com.practica.sistema_resttemplate.util.Util;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Log4j2
@Service
public class InfoSunatServiceImpl implements InfoSunatService {
    private final RestTemplate restTemplate;
    private final RedisService redisService;

    @Value("${token.api}")
    private String token;

    @Override
    public ResponseSunat getInfoSunat(String ruc) throws IOException {
        ResponseSunat datosSunat = new ResponseSunat();
        //Recupero la Información de Redis
        String redisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_SUNAT+ruc);
        //Valido que exista la info
        if(Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
            return datosSunat;
        }else{
            //Sino existe la data en redis me voy a Reniec api
            datosSunat = executeRestTemplate(ruc);
            //Convertir a String para poder guardarlo en Redis
            String dataForRedis = Util.convertirAString(datosSunat);
            //Guardando en Redis la información
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constants.REDIS_TTL);
            return datosSunat;
        }
    }

    private ResponseSunat executeRestTemplate(String ruc){
        //Configurar una URL completa como String
        String url = Constants.BASE_URL +  "/v2/sunat/ruc/full?numero="+ruc;
        //Genero mi CLient RestTemplate y Ejecuto
        ResponseEntity<ResponseSunat> executeRestTemplate =
                restTemplate.exchange(
                        url, //URL A LA CUAL VAS A EJECUTAR
                        HttpMethod.GET, //TIPO DE SOLICITUD AL QUE PERTENCE LA URL
                        new HttpEntity<>(createHeaders()), //CABECERAS || HEADERS
                        ResponseSunat.class // RESPONSE A CASTEAR
                );
        if(executeRestTemplate.getStatusCode().equals(HttpStatus.OK)){
            return executeRestTemplate.getBody();
        }else {
            return null;
        }
    }

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+token);
        return headers;
    }
}
