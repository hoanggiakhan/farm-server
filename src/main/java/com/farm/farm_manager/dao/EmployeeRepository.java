package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "employees")
public interface EmployeeRepository extends JpaRepository<Employee , Integer> {
    Employee findByUsername(String username);
    Employee findByFullName(String nameEmployee);
    boolean existsByUsername(String username);
}
