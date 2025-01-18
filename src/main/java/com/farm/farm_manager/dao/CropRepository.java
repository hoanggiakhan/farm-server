package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "crops")
public interface CropRepository extends JpaRepository<Crop,Integer> {
    Crop findByCropName(String cropName);
}
