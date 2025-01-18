package com.farm.farm_manager.service.animal;

import com.farm.farm_manager.dao.*;
import com.farm.farm_manager.dto.request.SellData;
import com.farm.farm_manager.dto.request.SellDataAnimal;
import com.farm.farm_manager.dto.response.AnimalResponse;
import com.farm.farm_manager.entity.*;
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
public class AnimalService {
    AnimalRepository animalRepository;
    EmployeeRepository employeeRepository;
    FarmRepository farmRepository;
    TransactionRepository transactionRepository;
    NotificationRepository notificationRepository;
    public List<Animal> getAllAnimal(){
//        List<Animal> animals = animalRepository.findAll();
//        List<AnimalResponse> animalResponses = new ArrayList<>();
//        for(Animal animal : animals){
//            AnimalResponse animalResponse =
//        }
        return animalRepository.findAll();
    }
    public void deleteAnimal(int animalId){

        animalRepository.deleteById(animalId);

    }
    public void createAnimal(int userId , Animal animal){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow();
        animal.setFarm(farm);
        animalRepository.save(animal);
        Transaction transaction = new Transaction();
        transaction.setType("Chi phí");
        transaction.setMoney(animal.getQuantity()*animal.getImportPrice());
        transaction.setDescription("Mua "+animal.getQuantity()+" con "+animal.getAnimalName());
        transaction.setFarm(farm);
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
        Notifications notifications = new Notifications();
        notifications.setDate(LocalDate.now());
        notifications.setStatus(0);
        notifications.setEmployee(employee);
        notifications.setContent("Bạn vừa thêm "+animal.getQuantity()+" con "+animal.getAnimalName());
        notificationRepository.save(notifications);
    }
   public void sellAnimal(SellDataAnimal sellData , int animalId , int userId){
       Animal animal = animalRepository.findById(animalId)
               .orElseThrow(() -> new IllegalArgumentException("Animal không tồn tại với ID: " + animalId));

       if (sellData.getQuantity() > animal.getQuantity()) {
           throw new IllegalArgumentException("Số lượng bán vượt quá số lượng hiện có.");
       }
       Transaction transaction = new Transaction();
       transaction.setType("Doanh thu");
       transaction.setMoney(sellData.getQuantity()*sellData.getSellPrice());
       transaction.setDescription("Bán "+sellData.getQuantity()+" con "+animal.getAnimalName());
       transaction.setDate(LocalDate.now());
       transaction.setFarm(animal.getFarm());
       transactionRepository.save(transaction);
       Employee employee = employeeRepository.findById(userId).orElseThrow();
       Notifications notifications = new Notifications();
       notifications.setDate(LocalDate.now());
       notifications.setStatus(0);
       notifications.setContent("Bạn vừa bán "+sellData.getQuantity()+" con "+animal.getAnimalName());
       notifications.setEmployee(employee);
       notificationRepository.save(notifications);
       // Trừ số lượng đã bán khỏi số lượng hiện có
       animal.setQuantity(animal.getQuantity() - sellData.getQuantity());

       // Kiểm tra nếu số lượng còn lại là 0 thì xóa bản ghi
       if (animal.getQuantity() == 0) {
           animalRepository.deleteById(animalId);
       } else {
           // Lưu lại cập nhật trong cơ sở dữ liệu nếu không xóa
           animalRepository.saveAndFlush(animal);
       }
   }
}
