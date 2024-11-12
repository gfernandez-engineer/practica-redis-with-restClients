package com.practica.sistema_retrofit.controller;

import com.practica.sistema_retrofit.response.ResponseSunat;
import com.practica.sistema_retrofit.service.InfoSunatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/retrofit")
@RequiredArgsConstructor
public class InfoSunatController {
    private final InfoSunatService infoSunatService;

    @GetMapping
    public ResponseEntity<ResponseSunat> getInfoSunat(
            @RequestParam("ruc") String ruc) throws IOException {
        return new ResponseEntity<>(infoSunatService.getInfoSunat(ruc),
                HttpStatus.OK);
    }
}
