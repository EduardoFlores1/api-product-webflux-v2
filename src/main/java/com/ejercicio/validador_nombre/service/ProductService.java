package com.ejercicio.validador_nombre.service;

import com.ejercicio.validador_nombre.model.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductEntity> findAll();
    Flux<ProductEntity> getAllAsStream();
    Flux<String> getPrevious();
    Mono<ProductEntity> findById(Long id);
    Mono<ProductEntity> insert(ProductEntity product);
    Mono<ProductEntity> updateById(ProductEntity product);
    Mono<Void> deleteById(Long id);
}
