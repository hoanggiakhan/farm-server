package com.farm.farm_manager.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne
    @JoinColumn(name = "farm_id")
    Farm farm;
    @OneToMany(mappedBy = "crop" , cascade = CascadeType.ALL)
    List<Task> tasks;
    @OneToMany(mappedBy = "crop" , cascade = CascadeType.ALL)
    List<Harvest> harvests;
}
