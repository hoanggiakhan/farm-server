package com.farm.farm_manager.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int farmId;
    String farmName;
    String address;
    @OneToMany(mappedBy = "farm" , cascade = CascadeType.ALL)
    List<Crop> crops;
    @OneToMany(mappedBy = "farm" , cascade = CascadeType.ALL)
    List<Animal> animals;
    @OneToMany(mappedBy = "farm" , cascade = CascadeType.ALL)
    List<Employee> employees;
    @OneToMany(mappedBy = "farm" , cascade = CascadeType.ALL)
    List<Inventory> inventories;
    @OneToMany(mappedBy = "farm" , cascade = CascadeType.ALL)
    List<Transaction> transactions;
}
