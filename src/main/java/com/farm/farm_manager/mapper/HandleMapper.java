package com.farm.farm_manager.mapper;

import com.farm.farm_manager.dto.request.*;
import com.farm.farm_manager.dto.response.*;
import com.farm.farm_manager.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HandleMapper {
    HandleMapper INSTANCE = Mappers.getMapper(HandleMapper.class);
    CropResponse toCrop(Crop crop);
    TaskResponse toTask(Task task);
    EmployeeResponse toEmployee(Employee employee);
     AnimalResponse toAnimal(Animal animal);
     Task toTaskRequest(TaskRequest request);
     Farm toFarmRequest(FarmRequest request);
     Employee toFarmRequests(FarmRequest request);
     Employee toEmployeeRequest(EmployeeRequest request);
     Crop toCropRequest(CropRequest request);
     Harvest toHarvestRequest(HarvestRequest request);
     HarvestResponse toHarvest(Harvest harvest);
     TransactionResponse toTransaction(Transaction transaction);
     NotificationResponse toNotifications(Notifications notifications);
     AttendanceResponse toAttendance(Attendance attendance);
    @Mapping(target = "employeeId", ignore = true)  // Ensure ID is not overwritten
    void updateEmployeeFromRequest(EmployeeRequest request, @MappingTarget Employee employee);

}
