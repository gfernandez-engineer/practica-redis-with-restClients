package com.practica.sistema_resttemplate.controller;


import com.practica.sistema_resttemplate.aggregates.response.ResponseSunat;
import com.practica.sistema_resttemplate.sunatService.InfoSunatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/restTemplate")
//@RequiredArgsConstructor
public class InfoSunatController {
    private final InfoSunatService infoSunatService;

    public InfoSunatController(InfoSunatService infoSunatService) {
        this.infoSunatService = infoSunatService;
    }

    @GetMapping("/infoSunat")
    public ResponseEntity<ResponseSunat> getInfoSunat(
            @RequestParam("ruc") String ruc) throws IOException {
        return new ResponseEntity<>(infoSunatService.getInfoSunat(ruc), HttpStatus.OK);
    }
}