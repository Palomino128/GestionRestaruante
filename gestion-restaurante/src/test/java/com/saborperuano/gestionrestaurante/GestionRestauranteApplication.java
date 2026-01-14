package com.saborperuano.gestionrestaurante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionRestauranteApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionRestauranteApplication.class, args);
    }
}

