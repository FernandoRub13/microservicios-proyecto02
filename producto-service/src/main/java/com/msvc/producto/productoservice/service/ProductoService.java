package com.msvc.producto.productoservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msvc.producto.productoservice.dto.ProductoRequest;
import com.msvc.producto.productoservice.dto.ProductoResponse;
import com.msvc.producto.productoservice.model.Producto;
import com.msvc.producto.productoservice.repository.ProductoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductoService {


    @Autowired
    private ProductoRepository productoRepository;

    public void createProducto(ProductoRequest productoRequest) {
        Producto producto = Producto.builder()
                .nombre(productoRequest.getNombre())
                .descripcion(productoRequest.getDescripcion())
                .precio(productoRequest.getPrecio())
                .build();
        productoRepository.save(producto);
        log.info("Producto creado con éxito: {}", producto.getId());
    }

    public List<ProductoResponse> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        log.info("Productos encontrados: {}", productos.size());
            return productos.stream().map(this::mapToProductsResponse).collect(Collectors.toList());
    }

    private ProductoResponse mapToProductsResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .build();
    }
}
