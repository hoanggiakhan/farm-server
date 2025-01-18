package com.farm.farm_manager.controller;

import com.farm.farm_manager.dto.request.CropRequest;
import com.farm.farm_manager.entity.Crop;
import com.farm.farm_manager.service.crop.CropService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crop")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CropController {
    CropService cropService;
    @GetMapping
    List<Crop> getAllCrop(){
        return cropService.getAllCrop();
    }
    @DeleteMapping("/delete-crop/{cropId}")
    void deleteCrop(@PathVariable int cropId){
        cropService.deleteCrop(cropId);
    }
    @PostMapping("/{userId}")
    void createCrop(@PathVariable int userId , @RequestBody CropRequest request){
        cropService.createCrop(userId,request);
    }
    @GetMapping("/one")
    Crop getCrop(@RequestBody CropRequest request){
      return   cropService.getCrop(request);
    }
    @PutMapping("/update/{cropId}")
    void updateCrop(@RequestBody CropRequest request , @PathVariable int cropId){
        cropService.updateCrop(request,cropId);
    }
}
