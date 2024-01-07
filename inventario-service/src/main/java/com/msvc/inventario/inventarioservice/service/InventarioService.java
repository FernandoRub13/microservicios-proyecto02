package com.msvc.inventario.inventarioservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msvc.inventario.inventarioservice.dto.InventarioResponse;
import com.msvc.inventario.inventarioservice.repository.InventarioRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InventarioService {
    
    @Autowired
    private InventarioRepository inventarioRepository;

    @Transactional(readOnly = true)
    
    @SneakyThrows
    public List<InventarioResponse> isInStock(List<String> codigoSku) {
        log.info("Wait started");
        // Thread.sleep(10000);
        log.info("Wait finished");
        return inventarioRepository.findByCodigoSkuIn(codigoSku)
            .stream()
            .map(inventario ->
                InventarioResponse.builder()
                .codigoSku(inventario.getCodigoSku())
                .isInStock(inventario.getCantidad() > 0)
                .build()
            ).collect(Collectors.toList());
    }
}
