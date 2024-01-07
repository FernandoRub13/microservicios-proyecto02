package com.msvc.producto.productoservice.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Producto {
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
}
