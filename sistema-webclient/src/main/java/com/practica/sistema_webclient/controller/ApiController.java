package com.practica.sistema_webclient.controller;

import com.practica.sistema_webclient.apiService.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ApiController {
    @Autowired
    private ApiService apiService;

    @GetMapping("/external-posts")
    public Mono<String> getExternalPosts() {
        return apiService.getPosts2();  // Llama al servicio para obtener los posts
    }

    @GetMapping("/external-combinated")
    public Mono<String> getCombinedDat() {
        return apiService.getCombinedData();  // Llama al servicio para obtener los posts
    }
}
