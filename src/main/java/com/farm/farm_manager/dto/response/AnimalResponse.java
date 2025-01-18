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
public class AnimalResponse {
    int animalId;  // mã vật nuôi
    String animalName; // tên vật nuôi
    double sellPrice;  // giá bán
    double importPrice;  // giá nhập
    int quantity;  // số lượng
    int status;  // trạng thái
    double age;
    LocalDate buyDate;
    int type;
}
