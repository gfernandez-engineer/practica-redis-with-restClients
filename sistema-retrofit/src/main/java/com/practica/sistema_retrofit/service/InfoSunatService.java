package com.practica.sistema_retrofit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practica.sistema_retrofit.response.ResponseSunat;

import java.io.IOException;

public interface InfoSunatService{
    ResponseSunat getInfoSunat(String ruc) throws IOException;
}
