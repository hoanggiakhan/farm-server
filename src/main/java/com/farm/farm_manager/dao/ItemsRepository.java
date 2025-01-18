package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "items")
public interface ItemsRepository extends JpaRepository<Items,Integer> {
}
