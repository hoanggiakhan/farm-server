package com.farm.farm_manager.service.farm;

import com.farm.farm_manager.dao.*;
import com.farm.farm_manager.dto.request.FarmRequest;
import com.farm.farm_manager.dto.response.*;
import com.farm.farm_manager.entity.*;
import com.farm.farm_manager.mapper.HandleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class FarmService {
    FarmRepository farmRepository;
    EmployeeRepository employeeRepository;
    PasswordEncoder passwordEncoder;
    InventoryRepository inventoryRepository;
    NotificationRepository notificationRepository;
    public List<CropResponse> getAllCropByFarm(int userId){
        Farm farm = getFarm(userId);
        List<Crop> crops = farm.getCrops();
        List<CropResponse> cropResponses = new ArrayList<>();
        for(Crop crop : crops){
            CropResponse cropResponse = HandleMapper.INSTANCE.toCrop(crop);
            cropResponses.add(cropResponse);
        }
        return cropResponses;
    }
    public List<AnimalResponse> getAllAnimalByFarm(int userId){
        Farm farm = getFarm(userId);
        List<Animal> animals = farm.getAnimals();
        List<AnimalResponse> animalResponses = new ArrayList<>();
        for(Animal animal : animals){
            AnimalResponse animalResponse = HandleMapper.INSTANCE.toAnimal(animal);
            animalResponses.add(animalResponse);
        }
        return animalResponses;
    }

    public List<EmployeeResponse> getAllEmployeeByFarm(int userId){
        Farm farm = getFarm(userId);
        List<Employee> employees = farm.getEmployees();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for(Employee employee : employees){
            EmployeeResponse employeeResponse = HandleMapper.INSTANCE.toEmployee(employee);
            if(employeeResponse.getEmployeeId()!=userId){
                employeeResponses.add(employeeResponse);
            }
        }
        return employeeResponses;
    }

    public List<InventoryResponse> getAllInventoryByFarm(int userId){
        Farm farm = getFarm(userId);
        List<Inventory> inventories = farm.getInventories();
        List<InventoryResponse> inventoryResponses = new ArrayList<>();
        for(Inventory inventory : inventories){
            InventoryResponse inventoryResponse = new InventoryResponse();
            inventoryResponse.setInventoryId(inventory.getInventoryId());
            inventoryResponses.add(inventoryResponse);
        }
        return inventoryResponses;
    }
   public List<TransactionResponse> getAllTransactionByFarm(int userId){
        Farm farm = getFarm(userId);
       List<TransactionResponse> transactionResponses = new ArrayList<>();
       for(Transaction transaction : farm.getTransactions()){
           TransactionResponse transactionResponse = HandleMapper.INSTANCE.toTransaction(transaction);
           transactionResponses.add(transactionResponse);
       }
        return transactionResponses;
   }
//    public List<RoleResponse> getAlRoleByFarm(int userId){
//        Farm farm = getFarm(userId);
//        List<RoleResponse> roleResponses = new ArrayList<>();
//        for(Role role : farm.getRoles()){
//            RoleResponse roleResponse = new RoleResponse();
//            roleResponse.setRoleName(role.getRoleName());
//            roleResponse.setDescription(role.getDescription());
//            roleResponses.add(roleResponse);
//        }
//        return roleResponses;
//    }

    public ResponseEntity<?> createFarm(FarmRequest request){
        Farm farm = HandleMapper.INSTANCE.toFarmRequest(request);
        farm.setAddress(request.getAddressFarm());
        Employee employee = HandleMapper.INSTANCE.toFarmRequests(request);
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setRole("ADMIN");
        employee.setFarm(farm);
        if(employeeRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body(new Notification("Username đã tồn tại"));
        }
        Inventory inventory = new Inventory();
        inventory.setFarm(farm);
        farmRepository.save(farm);
        inventoryRepository.save(inventory);
        employeeRepository.save(employee);
        Notifications notifications = new Notifications();
        notifications.setStatus(0);
        notifications.setContent("Chào mừng bạn đến với trang web của chúng tôi");
        notifications.setDate(LocalDate.now());
        notifications.setEmployee(employee);
        notificationRepository.save(notifications);
        return ResponseEntity.ok("Đăng ký thành công");
    }
    public Farm getFarm(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow(()-> new RuntimeException("Không tiìm thấy farm"));
        return farm;
    }


//    public ReportResponse report(int userId){
//        Employee employee = employeeRepository.findById(userId).orElseThrow();
//        Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow();
//
//    }
}
