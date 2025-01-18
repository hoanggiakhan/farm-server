package com.farm.farm_manager.service.inventory;

import com.farm.farm_manager.dao.*;
import com.farm.farm_manager.dto.request.ItemRequest;
import com.farm.farm_manager.dto.response.ItemResponse;
import com.farm.farm_manager.entity.*;
import com.farm.farm_manager.mapper.HandleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class InventoryService {
    InventoryRepository inventoryRepository;
    ItemsRepository itemsRepository;
    EmployeeRepository employeeRepository;
    FarmRepository farmRepository;
    TransactionRepository transactionRepository;
    NotificationRepository notificationRepository;
   public List<ItemResponse> getAllItemByInventory(int inventoryId){
       Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow();
       List<Items> items = inventory.getItems();
       List<ItemResponse> itemResponses = new ArrayList<>();
       for(Items item : items){
           ItemResponse itemResponse = new ItemResponse();
           itemResponse.setItemId(item.getItemId());
           itemResponse.setItemName(item.getItemName());
           itemResponse.setUnit(item.getUnit());
           itemResponse.setType(item.getType());
           itemResponse.setQuantity(item.getQuantity());
           itemResponse.setImportPrice(item.getImportPrice());
           itemResponses.add(itemResponse);
       }
       return itemResponses;
   }

   public void addItem(Items request, int inventoryId){
       Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow();
       request.setInventory(inventory);
       Transaction transaction = new Transaction();
       transaction.setDescription("Mua "+request.getQuantity()+" "+request.getUnit()+" "+request.getItemName());
       transaction.setMoney(request.getImportPrice()* request.getQuantity());
       transaction.setType("Chi phí");
       transaction.setDate(LocalDate.now());
       transaction.setFarm(inventory.getFarm());
       transactionRepository.save(transaction);

       itemsRepository.save(request);
   }
    public void createInventory(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow(()-> new RuntimeException("Không tiìm thấy farm"));
        Inventory inventory = new Inventory();
        inventory.setFarm(farm);
        inventoryRepository.save(inventory);
        Notifications notifications = new Notifications();
        notifications.setStatus(0);
        notifications.setContent("Bạn vừa thêm kho cho nông trại");
        notifications.setEmployee(employee);
        notifications.setDate(LocalDate.now());
        notificationRepository.save(notifications);
    }
    public void deleteInventory(int inventoryId){
       inventoryRepository.deleteById(inventoryId);
    }
   public void deleteItem(int itemId){
      itemsRepository.deleteById(itemId);
   }
}
