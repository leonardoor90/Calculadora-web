package com.leonardocalculator.Calculadoraweb; // Certifique-se que o pacote está correto

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // ESSA ANOTAÇÃO É CRUCIAL
public class CalculadoraWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculadoraWebApplication.class, args);
    }

}
