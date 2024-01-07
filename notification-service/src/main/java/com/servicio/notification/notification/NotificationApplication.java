package com.servicio.notification.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.servicio.notification.notification.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic", groupId = "notificationId")
	public void handleNotificationEvent(OrderPlacedEvent orderPlaceEvent) {
		log.info("Notificacion recibida desde Order: {}", orderPlaceEvent.getNumeroPedido());
	}

}
