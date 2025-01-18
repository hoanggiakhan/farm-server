package com.farm.farm_manager.controller;

import com.farm.farm_manager.dto.request.HarvestRequest;
import com.farm.farm_manager.dto.request.SellData;
import com.farm.farm_manager.dto.response.HarvestResponse;
import com.farm.farm_manager.entity.Harvest;
import com.farm.farm_manager.service.harvest.HarvestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/harvest")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class HarvestController {
    HarvestService harvestService;
    @PostMapping("/add-harvest/{userId}")
    void createHarvest(@RequestBody HarvestRequest request , @PathVariable int userId){
        harvestService.createHarvest(request,userId);
    }
    @GetMapping("/{userId}")
    List<HarvestResponse> getAllHarvest(@PathVariable int userId){
      return   harvestService.getAllHarvest(userId);
    }
    @PutMapping("/sell-products/{harvestId}")
    void sellProducts(@PathVariable int harvestId , @RequestBody SellData sellData){
        harvestService.sellProducts(sellData,harvestId);
    }
}
