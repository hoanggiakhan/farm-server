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
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int animalId;  // mã vật nuôi
    String animalName; // tên vật nuôi
    double sellPrice;  // giá bán
    double importPrice;  // giá nhập
    int quantity;  // số lượng
    int status;  // trạng thái
    double age;
    int type;
    LocalDate buyDate;
    @ManyToOne
    @JoinColumn(name = "farm_id")
    Farm farm;
    @OneToMany(mappedBy = "animal" , cascade = CascadeType.ALL)
    List<Task> tasks;
}
