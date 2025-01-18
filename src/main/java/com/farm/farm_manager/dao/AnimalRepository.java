package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "animals")
public interface AnimalRepository extends JpaRepository<Animal,Integer> {
    Animal findByAnimalName(String animalName);
}
