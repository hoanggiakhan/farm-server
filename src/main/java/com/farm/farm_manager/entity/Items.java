package com.farm.farm_manager.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int itemId;
    String itemName;
    int quantity;
    String type;
    String unit; // đơn vị tính
    double importPrice;
    @ManyToOne
    @JoinColumn(name = "id_inventory")
    Inventory inventory;
}
