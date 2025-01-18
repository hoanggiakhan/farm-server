package com.farm.farm_manager.controller;

import com.farm.farm_manager.dto.request.SellDataAnimal;
import com.farm.farm_manager.entity.Animal;
import com.farm.farm_manager.service.animal.AnimalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnimalController {
    AnimalService animalService;

    @GetMapping
    List<Animal> getAllAnimal(){
        return animalService.getAllAnimal();
    }
    @PostMapping("/{userId}")
    void createAnimal(@PathVariable int userId , @RequestBody Animal animal){
        animalService.createAnimal(userId , animal);
    }
    @DeleteMapping("/delete-animal/{animalId}")
    void deleteAnimal(@PathVariable int animalId){
        animalService.deleteAnimal(animalId);
    }
    @PutMapping("/sell-animal/{animalId}/{userId}")
    void sellAnimal(@PathVariable int animalId , @RequestBody SellDataAnimal sellDataAnimal , @PathVariable int userId){
        animalService.sellAnimal(sellDataAnimal , animalId , userId);
    }
}
