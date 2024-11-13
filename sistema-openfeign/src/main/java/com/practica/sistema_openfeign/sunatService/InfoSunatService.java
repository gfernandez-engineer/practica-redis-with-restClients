package com.practica.sistema_openfeign.sunatService;

import com.practica.sistema_openfeign.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface InfoSunatService {
    ResponseSunat getInfoSunat(String ruc) throws IOException;
}
