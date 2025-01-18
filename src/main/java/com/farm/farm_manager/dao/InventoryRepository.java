package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "inventories")
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
}
