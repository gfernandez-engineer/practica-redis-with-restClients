package com.practica.sistema_resttemplate.sunatService;

import com.practica.sistema_resttemplate.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface InfoSunatService {
    ResponseSunat getInfoSunat(String ruc) throws IOException;
}
