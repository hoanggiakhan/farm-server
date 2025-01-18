package com.farm.farm_manager.controller;

import com.farm.farm_manager.dto.request.FarmRequest;
import com.farm.farm_manager.dto.response.*;
import com.farm.farm_manager.service.farm.FarmService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farm")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class FarmController {
    FarmService farmService;

    @GetMapping("/crops/{userId}")
    List<CropResponse> getAllCropByFarm(@PathVariable int userId){
        return farmService.getAllCropByFarm(userId);
    }
    @GetMapping("/animals/{userId}")
    List<AnimalResponse> getAllAnimalByFarm(@PathVariable int userId){
        return farmService.getAllAnimalByFarm(userId);
    }
    @GetMapping("/employees/{userId}")
    List<EmployeeResponse> getAllEmployeeByFarm(@PathVariable int userId){
        return farmService.getAllEmployeeByFarm(userId);
    }
    @GetMapping("/inventories/{userId}")
    List<InventoryResponse> getALlInventoryByFarm(@PathVariable int userId){
        return farmService.getAllInventoryByFarm(userId);
    }
    @PostMapping
    ResponseEntity<?> createFarm(@RequestBody FarmRequest request){
      return farmService.createFarm(request);
    }
//    @GetMapping("/role/{userId}")
//    List<RoleResponse> getAllRoleByFarm(@PathVariable int userId){
//        return farmService.getAlRoleByFarm(userId);
//    }
    @GetMapping("/transactions/{userId}")
    List<TransactionResponse> getAllTransactionByFarm(@PathVariable int userId){
        return farmService.getAllTransactionByFarm(userId);
    }
}
