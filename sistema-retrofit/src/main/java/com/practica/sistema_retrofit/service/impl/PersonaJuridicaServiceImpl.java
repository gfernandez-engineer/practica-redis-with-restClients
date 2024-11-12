package com.practica.sistema_retrofit.service.impl;

import com.practica.sistema_retrofit.aggregates.constants.Constants;
import com.practica.sistema_retrofit.entity.PersonaJuridicaEntity;
import com.practica.sistema_retrofit.redis.RedisService;
import com.practica.sistema_retrofit.repository.PersonaJuridicaRepository;
import com.practica.sistema_retrofit.response.ResponseSunat;
import com.practica.sistema_retrofit.retrofit.ClientSunatService;
import com.practica.sistema_retrofit.retrofit.impl.ClientSunatServiceImpl;
import com.practica.sistema_retrofit.service.PersonaJuridicaService;
import com.practica.sistema_retrofit.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

@Service
@Log4j2
public class PersonaJuridicaServiceImpl implements PersonaJuridicaService {

    private final PersonaJuridicaRepository personaJuridicaRepository;
    private final RedisService redisService;

    ClientSunatService sunatServiceRetrofit = ClientSunatServiceImpl
            .getRetrofitSunat()
            .create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;

    public PersonaJuridicaServiceImpl(PersonaJuridicaRepository personaJuridicaRepository, RedisService redisService) {
        this.personaJuridicaRepository = personaJuridicaRepository;
        this.redisService = redisService;
    }

    public PersonaJuridicaEntity guardar(String ruc) throws IOException {
        PersonaJuridicaEntity personaJuridica  = getEntity(ruc);
        if (Objects.nonNull(personaJuridica)){
            return personaJuridicaRepository.save(personaJuridica);
        }
        else{
            return null;
        }

    }

    private PersonaJuridicaEntity getEntity(String ruc) throws IOException {
        PersonaJuridicaEntity PersonaJuridicaEntity = new PersonaJuridicaEntity();

        //Preparando el objeto Reniec usando Retrofit
        Call<ResponseSunat> clientRetrofit = prepareSunatRetrofit(ruc);
        log.info("getEntity -> Se Preparo todo el cliente Retrofit, listo para ejecutar!");
        //Ejecuto a Reniec usando Retrofit:
        Response<ResponseSunat> executeSunat = clientRetrofit.execute();
        log.info("getEntity -> Cliente Retrofit Ejecutado");

        //Validar el resultado
        ResponseSunat datosSunat = null;
        if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
            datosSunat = executeSunat.body();
            log.error("getEntity -> valores del body: " + executeSunat.body().toString());
        }

        if(Objects.nonNull(datosSunat)){
            PersonaJuridicaEntity.setRazonSocial(datosSunat.getRazonSocial());
            PersonaJuridicaEntity.setTipoDocumento(datosSunat.getTipoDocumento());
            PersonaJuridicaEntity.setNumeroDocumento(datosSunat.getNumeroDocumento());
            PersonaJuridicaEntity.setTipoDocumento(datosSunat.getTipoDocumento());

            PersonaJuridicaEntity.setEstado(datosSunat.getEstado());

            PersonaJuridicaEntity.setCondicion(datosSunat.getCondicion());
            PersonaJuridicaEntity.setDireccion(datosSunat.getDireccion());
            PersonaJuridicaEntity.setUbigeo(datosSunat.getUbigeo());
            PersonaJuridicaEntity.setViaTipo(datosSunat.getViaTipo());
            PersonaJuridicaEntity.setViaNombre(datosSunat.getViaNombre());

            PersonaJuridicaEntity.setZonaCodigo(datosSunat.getZonaCodigo());
            PersonaJuridicaEntity.setZonaTipo(datosSunat.getZonaTipo());
            PersonaJuridicaEntity.setNumero(datosSunat.getNumero());
            PersonaJuridicaEntity.setInterior(datosSunat.getInterior());

            PersonaJuridicaEntity.setLote(datosSunat.getLote());
            PersonaJuridicaEntity.setDpto(datosSunat.getDpto());
            PersonaJuridicaEntity.setManzana(datosSunat.getManzana());
            PersonaJuridicaEntity.setKilometro(datosSunat.getKilometro());

            PersonaJuridicaEntity.setDistrito(datosSunat.getDistrito());
            PersonaJuridicaEntity.setProvincia(datosSunat.getProvincia());
            PersonaJuridicaEntity.setDepartamento(datosSunat.getDepartamento());

            PersonaJuridicaEntity.setUserCreated(Constants.USER_CREATED);
            PersonaJuridicaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }
        return PersonaJuridicaEntity;
    }
    
    private Call<ResponseSunat> prepareSunatRetrofit(String ruc){
        String tokenComplete = "Bearer " + token;
        log.info("prepareSunatRetrofit -> Ejecutando Metodo de Apoyo que crea el objeto retrofit completo\"");
        return  sunatServiceRetrofit.getInfoSunat(tokenComplete,ruc);
    }

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
            //Sino existe la data en redis me voy a Sunat api
            datosSunat = executeRetroFit(ruc);
            //Convertir a String para poder guardarlo en Redis
            String dataForRedis = Util.convertirAString(datosSunat);
            //Guardando en Redis la información
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constants.REDIS_TTL);
            return datosSunat;
        }
    }
    private ResponseSunat executeRetroFit(String ruc) throws IOException {
        PersonaJuridicaEntity PersonaJuridicaEntity = new PersonaJuridicaEntity();

        //Preparando el objeto Reniec usando Retrofit
        Call<ResponseSunat> clientRetrofit = prepareSunatRetrofit(ruc);
        log.info("getEntity -> Se Preparo todo el cliente Retrofit, listo para ejecutar!");
        //Ejecuto a Reniec usando Retrofit:
        Response<ResponseSunat> executeSunat = clientRetrofit.execute();
        log.info("getEntity -> Cliente Retrofit Ejecutado");

        //Validar el resultado
        ResponseSunat datosSunat = null;
        if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
            datosSunat = executeSunat.body();
            log.error("getEntity -> valores del body: " + executeSunat.body().toString());
        }
        if(Objects.nonNull(datosSunat)){
            PersonaJuridicaEntity.setRazonSocial(datosSunat.getRazonSocial());
            PersonaJuridicaEntity.setTipoDocumento(datosSunat.getTipoDocumento());
            PersonaJuridicaEntity.setNumeroDocumento(datosSunat.getNumeroDocumento());
            PersonaJuridicaEntity.setTipoDocumento(datosSunat.getTipoDocumento());

            PersonaJuridicaEntity.setEstado(datosSunat.getEstado());

            PersonaJuridicaEntity.setCondicion(datosSunat.getCondicion());
            PersonaJuridicaEntity.setDireccion(datosSunat.getDireccion());
            PersonaJuridicaEntity.setUbigeo(datosSunat.getUbigeo());
            PersonaJuridicaEntity.setViaTipo(datosSunat.getViaTipo());
            PersonaJuridicaEntity.setViaNombre(datosSunat.getViaNombre());

            PersonaJuridicaEntity.setZonaCodigo(datosSunat.getZonaCodigo());
            PersonaJuridicaEntity.setZonaTipo(datosSunat.getZonaTipo());
            PersonaJuridicaEntity.setNumero(datosSunat.getNumero());
            PersonaJuridicaEntity.setInterior(datosSunat.getInterior());

            PersonaJuridicaEntity.setLote(datosSunat.getLote());
            PersonaJuridicaEntity.setDpto(datosSunat.getDpto());
            PersonaJuridicaEntity.setManzana(datosSunat.getManzana());
            PersonaJuridicaEntity.setKilometro(datosSunat.getKilometro());

            PersonaJuridicaEntity.setDistrito(datosSunat.getDistrito());
            PersonaJuridicaEntity.setProvincia(datosSunat.getProvincia());
            PersonaJuridicaEntity.setDepartamento(datosSunat.getDepartamento());

            PersonaJuridicaEntity.setUserCreated(Constants.USER_CREATED);
            PersonaJuridicaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }
        return datosSunat;
    }
}
