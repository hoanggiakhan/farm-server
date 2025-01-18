package com.farm.farm_manager.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    int employeeId;
    String fullName;
    String username;
    String password;
    String address;
    String phoneNumber;
    int age;
    LocalDate joinDate;
    String email;
    double salary;
    String role;
}
