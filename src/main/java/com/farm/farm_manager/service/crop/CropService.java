package com.farm.farm_manager.service.crop;

import com.farm.farm_manager.dao.*;
import com.farm.farm_manager.dto.request.CropRequest;
import com.farm.farm_manager.entity.*;
import com.farm.farm_manager.mapper.HandleMapper;
import com.farm.farm_manager.service.employee.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CropService {
    CropRepository cropRepository;
    EmployeeRepository employeeRepository;
    FarmRepository farmRepository;
    TransactionRepository transactionRepository;
     NotificationRepository notificationRepository;
    public List<Crop> getAllCrop(){
        return cropRepository.findAll();
    }
    public void deleteCrop(int cropId){

        cropRepository.deleteById(cropId);

    }
    public Crop getCrop(CropRequest request){
        return cropRepository.findByCropName(request.getCropName());
    }

    public void createCrop(int userId , CropRequest request){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow();
        Crop crop = HandleMapper.INSTANCE.toCropRequest(request);
        crop.setFarm(farm);
        cropRepository.save(crop);
        Transaction transaction = new Transaction();
        transaction.setType("Chi phí");
        transaction.setDate(LocalDate.now());
        transaction.setMoney(request.getImportPrice()*request.getQuantity());
        transaction.setDescription("Mua "+request.getQuantity()+" "+request.getCropName());
        transaction.setFarm(farm);
        Notifications notifications = new Notifications();
        notifications.setDate(LocalDate.now());
        notifications.setStatus(0);
        notifications.setEmployee(employee);
        notifications.setContent("Bạn vừa thêm "+request.getQuantity()+" "+request.getCropName());
        notificationRepository.save(notifications);
        transactionRepository.save(transaction);
    }

    public void updateCrop(CropRequest request, int cropId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found with id: " + cropId));

        // Cập nhật các trường cần thiết từ request vào crop hiện tại
        crop.setCropName(request.getCropName());
        crop.setSellPrice(request.getSellPrice());
        crop.setImportPrice(request.getImportPrice());
        crop.setQuantity(request.getQuantity());
        crop.setStatus(request.getStatus());
        crop.setAge(request.getAge());
        crop.setPlantingDay(request.getPlantingDay());
        crop.setAcreage(request.getAcreage());
        crop.setProductivity(request.getProductivity());
        crop.setType(request.getType());

        // Lưu lại thông tin đã cập nhật
        cropRepository.saveAndFlush(crop);
    }

}
