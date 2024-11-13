package com.practica.sistema_webclient.apiService.apiServiceImpl;

import com.practica.sistema_webclient.apiService.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<String> getPosts1() {
        return webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .get()  // Método HTTP GET
                .uri("/posts/1")  // Accede al primer post de la API
                .retrieve()  // Realiza la solicitud
                .bodyToMono(String.class);  // Convierte la respuesta a un Mono<String>
    }
    @Override
    public Mono<String> getPosts2() {
        return webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .get()
                .uri("/posts/1")
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        clientResponse -> Mono.error(new RuntimeException("Client Error")))
                .onStatus(status -> status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(String.class)
                .onErrorMap(throwable -> new RuntimeException("Error occurred while fetching data", throwable));
    }

    @Override
    public Mono<String> getCombinedData() {
        Mono<String> postData = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .get()
                .uri("/posts/1")
                .retrieve()
                .bodyToMono(String.class);

        Mono<String> commentData = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .get()
                .uri("/comments/1")
                .retrieve()
                .bodyToMono(String.class);

        return postData.zipWith(commentData, (post, comment) -> post + "\n" + comment);
    }



}
