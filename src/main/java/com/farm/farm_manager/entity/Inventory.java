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
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int inventoryId;   // mã kho
    @OneToMany(mappedBy = "inventory" , cascade = CascadeType.ALL)
    List<Items> items;  // loại dụng cụ
    @ManyToOne
    @JoinColumn(name = "farm_id")
    Farm farm;
}
