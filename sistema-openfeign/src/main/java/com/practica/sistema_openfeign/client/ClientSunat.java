package com.practica.sistema_openfeign.client;

import com.practica.sistema_openfeign.aggregates.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client-sunat", url = "https://api.apis.net.pe/v2/sunat/ruc/" )
public interface ClientSunat {
    @GetMapping("/full")
    ResponseSunat getPersonaSunat(@RequestParam("numero") String numero,
                                  @RequestHeader("Authorization") String authorization);
}
