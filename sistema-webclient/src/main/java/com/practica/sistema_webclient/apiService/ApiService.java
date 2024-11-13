package com.practica.sistema_webclient.apiService;

import reactor.core.publisher.Mono;

public interface ApiService {
    public Mono<String> getPosts1();
    public Mono<String> getPosts2();
    public Mono<String> getCombinedData();
}
