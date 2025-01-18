package com.farm.farm_manager.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FarmRequest {
    String farmName;
    String addressFarm;
    String fullName;
    String username;
    String password;
    String address;
    String phoneNumber;
    String email;
}
