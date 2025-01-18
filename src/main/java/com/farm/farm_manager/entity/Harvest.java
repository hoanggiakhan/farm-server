package com.farm.farm_manager.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int harvestId; // mã phiếu thu hoạch
    LocalDate harvestDate; // ngày thu hoạch
    double quantity;  // số lượng
    double sellPrice;
    @ManyToOne
    @JoinColumn(name = "crop_id")
    Crop crop;
}
