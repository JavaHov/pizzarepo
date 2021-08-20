package com.example.demo;

import com.example.demo.entities.Pizza;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.demo.repositories.PizzaRepository;

@SpringBootApplication

public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner init(PizzaRepository pizzaRepository) {

        return (args) -> {

            if(pizzaRepository.count() == 0) {
                pizzaRepository.save(new Pizza(1L, "Hawaii", "Tomatsås,Ost,Skinka,Ananas", 80));
                pizzaRepository.save(new Pizza(2L, "Napoli", "Tomatsås,Ost,Skinka,Lök,Curry", 85));
                pizzaRepository.save(new Pizza(3L, "Quattro", "Tomatsås,Ost,Skinka,Räkor,Svamp", 85));
            }
        };

    }
}
