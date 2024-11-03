package com.ejercicio.validador_nombre.service;

import com.ejercicio.validador_nombre.model.dto.ProductRequest;
import com.ejercicio.validador_nombre.model.dto.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductResponse> findAll();
    Flux<ProductResponse> getAllAsStream();
    Flux<String> getPrevious();
    Mono<ProductResponse> findById(Long id);
    Mono<ProductResponse> insert(ProductRequest request);
    Mono<ProductResponse> updateById(Long id, ProductRequest request);
    Mono<Void> deleteById(Long id);
}
