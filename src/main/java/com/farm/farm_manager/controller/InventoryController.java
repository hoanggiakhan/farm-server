package com.farm.farm_manager.controller;

import com.farm.farm_manager.dto.response.ItemResponse;
import com.farm.farm_manager.entity.Items;
import com.farm.farm_manager.service.inventory.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class InventoryController {
    InventoryService inventoryService;

    @GetMapping("/items/{inventoryId}")
    List<ItemResponse> getAllItemByInventory(@PathVariable int inventoryId){
        return inventoryService.getAllItemByInventory(inventoryId);
    }

    @PostMapping("/add-item/{inventoryId}")
    void addItem(@RequestBody Items items , @PathVariable int inventoryId){
        inventoryService.addItem(items , inventoryId);
    }
   @PostMapping("/add-inventory/{userId}")
   void addInventory(@PathVariable int userId){
        inventoryService.createInventory(userId);
   }
   @DeleteMapping("/delete-inventory/{inventoryId}")
   void deleteInventory(@PathVariable int inventoryId){
        inventoryService.deleteInventory(inventoryId);
   }
    @DeleteMapping("/delete-item/{itemId}")

    void deleteItem(@PathVariable int itemId){
        inventoryService.deleteItem(itemId);
    }
}
