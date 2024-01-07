package com.msvc.order.orderservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.msvc.order.orderservice.dto.OrderRequest;
import com.msvc.order.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventario", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventario")
    @Retry(name = "inventario")
    public CompletableFuture<String> realizarPedido(@RequestBody OrderRequest orderRequest){
    
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest) );
    }

    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException e){
        return CompletableFuture.supplyAsync(() -> {
            return "Ooops! Ha ocurrido un error al realizar el pedido";
        });
    }
}
