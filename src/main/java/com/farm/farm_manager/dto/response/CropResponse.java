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
public class CropResponse {
    int cropId;  // mã cây trồng
    String cropName;  // tên cây trồng
    double sellPrice;  // giá bán
    double importPrice; // giá nhập
    int quantity;  // số lượng
    int status; // trạng thái
    double age;
    LocalDate plantingDay; // ngày gieo trồng
    double acreage; // diện tích gieo trồng
    int productivity; // năng suất
    int type;
}
