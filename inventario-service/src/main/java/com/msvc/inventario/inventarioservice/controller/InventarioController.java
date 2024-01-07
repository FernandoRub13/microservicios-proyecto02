package com.msvc.inventario.inventarioservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msvc.inventario.inventarioservice.dto.InventarioResponse;
import com.msvc.inventario.inventarioservice.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventarioResponse> isInStock(@RequestParam List<String> codigoSku) {
        return inventarioService.isInStock(codigoSku);
    }
}
