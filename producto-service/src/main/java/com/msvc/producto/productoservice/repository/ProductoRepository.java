package com.msvc.producto.productoservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msvc.producto.productoservice.model.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String>{
    
}
