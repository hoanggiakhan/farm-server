package com.farm.farm_manager.dto.request;

import com.farm.farm_manager.entity.Animal;
import com.farm.farm_manager.entity.Crop;
import com.farm.farm_manager.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequest {
    String title;
    String description;
    LocalDate date;
    String nameEmployee;
    String animalName;
    String cropName;
}
