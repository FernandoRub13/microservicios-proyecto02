package com.msvc.inventario.inventarioservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.msvc.inventario.inventarioservice.model.Inventario;
import com.msvc.inventario.inventarioservice.repository.InventarioRepository;

@SpringBootApplication
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventarioRepository inventarioRepository){
		return (args) -> {
			inventarioRepository.save(new Inventario(null, "A", 10));
			inventarioRepository.save(new Inventario(null, "B", 20));
			inventarioRepository.save(new Inventario(null, "C", 30));
			inventarioRepository.save(new Inventario(null, "D", 40));
			inventarioRepository.save(new Inventario(null, "E", 50));
			inventarioRepository.save(new Inventario(null, "F", 60));
			inventarioRepository.save(new Inventario(null, "G", 70));
			inventarioRepository.save(new Inventario(null, "H", 80));
			inventarioRepository.save(new Inventario(null, "I", 90));
			inventarioRepository.save(new Inventario(null, "J", 100));

			
		};
	} 

}
