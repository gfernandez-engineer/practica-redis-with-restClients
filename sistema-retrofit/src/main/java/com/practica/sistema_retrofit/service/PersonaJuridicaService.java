package com.practica.sistema_retrofit.service;

import com.practica.sistema_retrofit.entity.PersonaJuridicaEntity;
import com.practica.sistema_retrofit.response.ResponseSunat;

import java.io.IOException;

public interface PersonaJuridicaService {
    ResponseSunat getInfoSunat(String ruc) throws IOException;
    PersonaJuridicaEntity guardar(String ruc) throws IOException;
}
