package com.ejercicio.validador_nombre.model.dto;

import com.ejercicio.validador_nombre.model.entity.ProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank
    private String name;

    public static ProductDTO from(ProductEntity entity) {
        return ProductDTO.of(entity.getId(), entity.getName());
    }

    public ProductEntity toCreateEntity() {
        return ProductEntity.of(null, name);
    }

    public ProductEntity toUpdateEntity(Long productId) {
        return ProductEntity.of(productId, name);
    }
}
