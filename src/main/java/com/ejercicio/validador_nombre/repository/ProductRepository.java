package com.ejercicio.validador_nombre.repository;

import com.ejercicio.validador_nombre.model.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
}
