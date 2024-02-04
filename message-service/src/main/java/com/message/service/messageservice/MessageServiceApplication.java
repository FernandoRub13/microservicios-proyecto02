package com.message.service.messageservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MessageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}

	@RabbitListener(queues = {"${msvc.queue.name}"})
	public void recibirMensajeConRabbitMQ(String message) {
		log.info("Mensaje recibido: {}", message);
	}

}
