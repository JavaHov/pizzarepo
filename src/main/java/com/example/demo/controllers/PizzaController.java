package com.example.demo.controllers;


import com.example.demo.entities.Pizza;
import com.example.demo.repositories.PizzaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PizzaController {

    private PizzaRepository pizzaRepository;

    public PizzaController(PizzaRepository pizzaRepository) {

        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/pizzas")
    public List<Pizza> pizzas() {

        return pizzaRepository.findAll();
    }

    @GetMapping("/pizzas/{id}")
    public Optional<Pizza> getOnePizza(@PathVariable String id) {

        Long pizzaId = Long.parseLong(id);
        return pizzaRepository.findById(pizzaId);
    }

    @GetMapping(value = "/search/{searchstring}")
    public List<Pizza> searchInPizzas(@PathVariable String searchstring) {

        List<Pizza> pizzas = pizzaRepository.findAll();
        return pizzaRepository.findAll().stream().filter(p -> p.getIngredients().contains(searchstring)).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/deletepizzas/{id}")
    public void deletePizza(@PathVariable String id) {

        Long pizzaId = Long.parseLong(id);
        pizzaRepository.deleteById(pizzaId);
    }

    @PutMapping(value = "/addpizza/{newPizza}")
    public void addPizza(@PathVariable String newPizza) {

        String[] fields = newPizza.split("-");

        //(Long id, String name, String ingredients, int price)
        Pizza p = new Pizza(Long.parseLong(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]));
        pizzaRepository.save(p);
    }

    @PatchMapping(value = "/updatepizza/{updatestring}")

    public void updatePizza(@PathVariable String updatestring) {

        String[] updatesplit = updatestring.split("-");

        Pizza originPizza = pizzaRepository.getById(Long.parseLong(updatesplit[0]));
        String[] originIngredients = originPizza.getIngredients().split(",");

        for(int i = 0; i < originIngredients.length; i++) {

            if(originIngredients[i].contains(updatesplit[1])) {
                originIngredients[i] = updatesplit[2];
            }
        }
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < originIngredients.length; i++) {

            if(i == originIngredients.length-1)
                buffer.append(originIngredients[i]);
            else {
                buffer.append(originIngredients[i]);
                buffer.append(",");
            }

        }

        String newIngredients = buffer.toString();

        originPizza.setIngredients(newIngredients);
        pizzaRepository.save(originPizza);
    }

}
