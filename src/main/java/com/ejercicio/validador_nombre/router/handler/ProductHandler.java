package com.ejercicio.validador_nombre.router.handler;

import com.ejercicio.validador_nombre.model.dto.ProductRequest;
import com.ejercicio.validador_nombre.model.dto.ProductResponse;
import com.ejercicio.validador_nombre.service.ProductService;
import com.ejercicio.validador_nombre.validator.ObjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll(), ProductResponse.class);
    }

    public Mono<ServerResponse> getAllAsStream(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(productService.getAllAsStream(), ProductResponse.class);
    }

    public Mono<ServerResponse> getPrevious(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(productService.getPrevious(), String.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("productId"));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findById(id), ProductResponse.class);
    }

    public Mono<ServerResponse> insertOne(ServerRequest request) {
        return request
                .bodyToMono(ProductRequest.class)
                .doOnNext(objectValidator::validate)
                .flatMap(requestCreate ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(productService.insert(requestCreate), ProductResponse.class));

    }

    public Mono<ServerResponse> updateById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("productId"));
        return request
                .bodyToMono(ProductRequest.class)
                .doOnNext(objectValidator::validate)
                .flatMap(requestUpdate ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(productService.updateById(id, requestUpdate), ProductResponse.class));

    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("productId"));

        return ServerResponse
                .status(HttpStatus.NO_CONTENT)
                .body(productService.deleteById(id), Void.class);
    }

}
