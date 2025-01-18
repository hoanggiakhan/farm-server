package com.farm.farm_manager.dao;

import com.farm.farm_manager.entity.Employee;
import com.farm.farm_manager.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,String> {
    void deleteByEmployee(Employee employee);
}
