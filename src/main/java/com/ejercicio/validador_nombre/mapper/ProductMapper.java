package com.ejercicio.validador_nombre.mapper;

import com.ejercicio.validador_nombre.model.dto.ProductRequest;
import com.ejercicio.validador_nombre.model.dto.ProductResponse;
import com.ejercicio.validador_nombre.model.entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity requestToEntity(ProductRequest request) {
        return ProductEntity.of(null, request.getName());
    }

    public static ProductResponse entityToResponse(ProductEntity entity) {
        return ProductResponse.of(entity.getId(), entity.getName());
    }
}
