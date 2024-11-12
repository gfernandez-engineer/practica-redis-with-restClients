package com.practica.sistema_retrofit.controller;


import com.practica.sistema_retrofit.entity.PersonaJuridicaEntity;
import com.practica.sistema_retrofit.response.ResponseSunat;
import com.practica.sistema_retrofit.service.PersonaJuridicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/personaJuridica")
public class PersonaJuridicaController {

    private final PersonaJuridicaService personaJuridicaService;

    public PersonaJuridicaController(PersonaJuridicaService personaJuridicaService) {
        this.personaJuridicaService = personaJuridicaService;
    }

    public ResponseEntity<ResponseSunat> getInfoSunat(
            @PathVariable String ruc) throws IOException {
        return new ResponseEntity<>(personaJuridicaService
                .getInfoSunat(ruc), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PersonaJuridicaEntity> guardarPersona (
            @RequestParam("ruc") String ruc) throws IOException {
        PersonaJuridicaEntity personaJuridica = personaJuridicaService.guardar(ruc);
        return new ResponseEntity<>(personaJuridica, HttpStatus.CREATED);
    }
}

