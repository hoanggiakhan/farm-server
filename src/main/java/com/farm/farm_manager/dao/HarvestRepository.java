package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "harvests")
public interface HarvestRepository extends JpaRepository<Harvest,Integer> {
}
