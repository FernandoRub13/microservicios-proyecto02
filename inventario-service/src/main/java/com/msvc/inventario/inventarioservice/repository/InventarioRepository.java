package com.msvc.inventario.inventarioservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msvc.inventario.inventarioservice.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    
    List<Inventario> findByCodigoSkuIn(List<String> codigoSku);
}
