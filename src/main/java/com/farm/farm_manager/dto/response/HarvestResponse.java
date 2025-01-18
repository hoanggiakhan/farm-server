package com.farm.farm_manager.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HarvestResponse {
    int harvestId;
    LocalDate harvestDate; // ngày thu hoạch
    double quantity;  // số lượng
    double sellPrice; // Giá bán
    String cropName;
}
