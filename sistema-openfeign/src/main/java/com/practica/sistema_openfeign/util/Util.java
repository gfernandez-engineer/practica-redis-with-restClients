package com.practica.sistema_openfeign.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String convertirAString(T objeto)
            throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(objeto);
    }

    public static <T> T convertirDesdeString(String datoDeRedis, Class<T> tipoClase)
            throws JsonProcessingException{
        return OBJECT_MAPPER.readValue(datoDeRedis,tipoClase);
    }

    /*public static <T> String convertirAString(T objeto){
        try {
            return OBJECT_MAPPER.writeValueAsString(objeto);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir la Clase a Cadena(String) : "+ e);
        }
    }

    public static <T> T convertirDesdeString(String datoDeRedis, Class<T> tipoClase){
        try {
            return OBJECT_MAPPER.readValue(datoDeRedis,tipoClase);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir desde String a la Clase: "+ e);
        }
    }*/
}
