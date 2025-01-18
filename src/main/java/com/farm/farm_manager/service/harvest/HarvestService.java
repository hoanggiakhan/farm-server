package com.farm.farm_manager.service.harvest;

import com.farm.farm_manager.dao.*;
import com.farm.farm_manager.dto.request.HarvestRequest;
import com.farm.farm_manager.dto.request.SellData;
import com.farm.farm_manager.dto.response.HarvestResponse;
import com.farm.farm_manager.entity.*;
import com.farm.farm_manager.mapper.HandleMapper;
import com.farm.farm_manager.service.employee.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class HarvestService {
    HarvestRepository harvestRepository;
    FarmRepository farmRepository;
    EmployeeRepository employeeRepository;
    TransactionRepository transactionRepository;
    NotificationRepository notificationRepository;
    public void createHarvest(HarvestRequest request, int userId) {
        // Chuyển đổi request thành đối tượng Harvest
        Harvest harvest = HandleMapper.INSTANCE.toHarvestRequest(request);

        // Lấy thông tin nhân viên và nông trại từ cơ sở dữ liệu
        Employee employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Farm farm = farmRepository.findById(employee.getFarm().getFarmId())
                .orElseThrow(() -> new RuntimeException("Farm not found for user ID: " + userId));

        // Kiểm tra tên cây trồng không null hoặc rỗng
        if (request.getCropName() != null && !request.getCropName().trim().isEmpty()) {
            // Tìm cây trồng khớp với tên trong danh sách cây của nông trại
            Optional<Crop> matchingCrop = farm.getCrops().stream()
                    .filter(crop -> crop.getCropName().equals(request.getCropName()))
                    .findFirst();

            // Nếu tìm thấy cây trồng, set crop cho harvest, nếu không, báo lỗi
            if (matchingCrop.isPresent()) {
                harvest.setCrop(matchingCrop.get());
            } else {
                throw new RuntimeException("Crop with name " + request.getCropName() + " not found in farm.");
            }
        } else {
            throw new IllegalArgumentException("Crop name cannot be null or empty.");
        }

        // Lưu harvest vào cơ sở dữ liệu
        harvestRepository.save(harvest);
        Notifications notifications = new Notifications();
        notifications.setDate(LocalDate.now());
        notifications.setStatus(0);
        notifications.setEmployee(employee);
        notifications.setContent("Bạn vừa thu hoạch "+request.getQuantity()+" KG nông sản "+request.getCropName());
        notificationRepository.save(notifications);
    }


    public List<HarvestResponse> getAllHarvest(int userId) {
        // Lấy thông tin nhân viên và nông trại từ cơ sở dữ liệu
        Employee employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Farm farm = farmRepository.findById(employee.getFarm().getFarmId())
                .orElseThrow(() -> new RuntimeException("Farm not found for user ID: " + userId));

        // Lấy danh sách tất cả Harvest của các Crop trong Farm và chuyển đổi thành HarvestResponse
        return farm.getCrops().stream()
                .flatMap(crop -> crop.getHarvests().stream()) // Lấy tất cả Harvest của từng Crop
                .map(harvest -> {
                    HarvestResponse harvestResponse = HandleMapper.INSTANCE.toHarvest(harvest);
                    harvestResponse.setCropName(harvest.getCrop().getCropName());
                    return harvestResponse;
                })
                .collect(Collectors.toList()); // Chuyển đổi thành danh sách HarvestResponse
    }

    public void sellProducts(SellData sellData, int harvestId) {
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest không tồn tại với ID: " + harvestId));

        if (sellData.getQuantity() > harvest.getQuantity()) {
            throw new IllegalArgumentException("Số lượng bán vượt quá số lượng hiện có.");
        }
        Transaction transaction = new Transaction();
        transaction.setType("Doanh thu");
        transaction.setMoney(sellData.getQuantity()*sellData.getSellPrice());
        transaction.setDescription("Bán "+sellData.getQuantity()+" KG nông sản "+harvest.getCrop().getCropName());
        transaction.setDate(LocalDate.now());
        transaction.setFarm(harvest.getCrop().getFarm());
        transactionRepository.save(transaction);
        // Trừ số lượng đã bán khỏi số lượng hiện có
        harvest.setQuantity(harvest.getQuantity() - sellData.getQuantity());

        // Kiểm tra nếu số lượng còn lại là 0 thì xóa bản ghi
        if (harvest.getQuantity() == 0) {
            harvestRepository.deleteById(harvestId);
        } else {
            // Lưu lại cập nhật trong cơ sở dữ liệu nếu không xóa
            harvestRepository.saveAndFlush(harvest);
        }
    }

}
