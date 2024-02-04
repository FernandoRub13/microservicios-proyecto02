package com.msvc.order.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.msvc.order.orderservice.config.rabbitmq.Producer;
import com.msvc.order.orderservice.dto.InventarioResponse;
import com.msvc.order.orderservice.dto.OrderLineItemsDto;
import com.msvc.order.orderservice.dto.OrderRequest;
import com.msvc.order.orderservice.event.OrderPlacedEvent;
import com.msvc.order.orderservice.model.Order;
import com.msvc.order.orderservice.model.OrderLineItems;
import com.msvc.order.orderservice.repository.OrderRepository;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
public class OrderService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Tracer tracer;

    @Autowired
    private Producer producer;


    // @Transactional(readOnly = true)
    public String placeOrder(OrderRequest orderRequest) {
        
        Order order = new Order();
        order.setNumeroPedido(UUID.randomUUID().toString());

        List<OrderLineItems> oderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        order.setOrderLineItems(oderLineItems);

        List<String> codigoSku = order.getOrderLineItems()
                .stream()
                .map(OrderLineItems::getCodigoSku)
                .collect(Collectors.toList());

        System.out.println("codigos sku: " + codigoSku);

        Span inventarioServiceLookup = tracer.nextSpan().name("inventarioServiceLookup");


        try(Tracer.SpanInScope isLookup = tracer.withSpan(inventarioServiceLookup.start())) {
            inventarioServiceLookup.tag("call", "inventario-service");     
            
            InventarioResponse[] inventarioResponse = webClientBuilder.build().get()
                    .uri("http://inventario-service/api/inventario", uriBuilder -> uriBuilder.queryParam("codigoSku", codigoSku).build())
                    .retrieve()
                    .bodyToMono(InventarioResponse[].class)
                    .block(); // This line is blocking, if you want to avoid this, you can use a reactive approach 
            
            boolean allProductosInStock = Arrays
                    .stream(inventarioResponse)
                    .allMatch(InventarioResponse::isInStock);
                if (allProductosInStock) {
                    orderRepository.save(order);
                    enviarMensajeConRabbitMQ("Notificacion con RabbitMQ, Pedido realizado con exito");
                    kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getNumeroPedido()).toString());
                    return "Orden realizada con exito";
                } else {
                    throw new IllegalArgumentException("No hay suficiente inventario para procesar el pedido");
                }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No hay suficiente inventario para procesar el pedido");
        } finally {
            inventarioServiceLookup.end();
        }

    }

    private void enviarMensajeConRabbitMQ(String message) {
        log.info("El mensaje ha sido enviado a la cola de RabbitMQ");
        producer.send(message);
    }

    private OrderLineItems mapToEntity(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setCodigoSku(orderLineItemsDto.getCodigoSku());
        orderLineItems.setPrecio(orderLineItemsDto.getPrecio());
        orderLineItems.setCantidad(orderLineItemsDto.getCantidad());
        return orderLineItems;
    }

}
