package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "farms")
public interface FarmRepository extends JpaRepository<Farm,Integer> {
}
