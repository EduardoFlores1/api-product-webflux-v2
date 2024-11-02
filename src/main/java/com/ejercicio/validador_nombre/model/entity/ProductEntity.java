package com.ejercicio.validador_nombre.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@Table("products")
public class ProductEntity {
    @Id
    private Long id;
    private String name;
}
