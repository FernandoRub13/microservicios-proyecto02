package com.msvc.producto.productoservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductoRequest {
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
}
