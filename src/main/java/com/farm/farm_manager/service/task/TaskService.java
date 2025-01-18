package com.farm.farm_manager.service.task;

import com.farm.farm_manager.dao.*;
import com.farm.farm_manager.dto.request.TaskRequest;
import com.farm.farm_manager.dto.response.TaskResponse;
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
public class TaskService {
    TaskRepository taskRepository;
    CropRepository cropRepository;
    AnimalRepository animalRepository;
    EmployeeRepository employeeRepository;
    FarmRepository farmRepository;
    NotificationRepository notificationRepository;
    public List<TaskResponse> getAllTaskByUserId(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        List<Task> tasks = employee.getTasks();
        List<TaskResponse> taskResponses = new ArrayList<>();
        for(Task task : tasks){
            TaskResponse taskResponse = HandleMapper.INSTANCE.toTask(task);
            if (taskResponse != null) {
                taskResponse.setNameEmployee(employee.getFullName());
                if(task.getAnimal() != null){
                    taskResponse.setAnimalName(task.getAnimal().getAnimalName());
                }
                if(task.getCrop() != null){
                    taskResponse.setAnimalName(task.getCrop().getCropName());
                }
                if(task.getAnimal() != null && task.getCrop() != null){
                    throw new RuntimeException("sai cấu trúc");
                }
                taskResponses.add(taskResponse);
            }
        }
        return taskResponses;
    }

    public List<TaskResponse> getAllTaskByFarm(int userId){
         Employee employee = employeeRepository.findById(userId).orElseThrow();
         Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow();
         List<TaskResponse> taskResponses = new ArrayList<>();
          List<Integer> employeeIds = new ArrayList<>();
          for(Employee employee1 : farm.getEmployees()){
              employeeIds.add(employee1.getEmployeeId());
          }
          for(int employeeId : employeeIds){
              for(TaskResponse taskResponse : getAllTaskByUserId(employeeId)){
                  taskResponses.add(taskResponse);
              }
          }
          return taskResponses;
    }

    public void createTask(TaskRequest request, int userId) {
        Task task = HandleMapper.INSTANCE.toTaskRequest(request);
        task.setDate(LocalDate.now().plusDays(1));
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow();
        if (request.getAnimalName() != null && !request.getAnimalName().isBlank() &&
                (request.getCropName() == null || request.getCropName().isBlank())) {
            farm.getAnimals().stream()
                    .filter(animal -> animal.getAnimalName().equals(request.getAnimalName()))
                    .findFirst()
                    .ifPresent(task::setAnimal);
        }
        if (request.getCropName() != null && !request.getCropName().isBlank() &&
                (request.getAnimalName() == null || request.getAnimalName().isBlank())) {
            farm.getCrops().stream()
                    .filter(crop -> crop.getCropName().equals(request.getCropName()))
                    .findFirst()
                    .ifPresent(task::setCrop);
        }
        if (request.getNameEmployee() != null && !request.getNameEmployee().isBlank()) {
            farm.getEmployees().stream()
                    .filter(emp -> emp.getFullName().equals(request.getNameEmployee()))
                    .findFirst()
                    .ifPresent(task::setEmployee);
        }
        taskRepository.save(task);
        Notifications notifications = new Notifications();
        notifications.setStatus(0);
        notifications.setContent("Bạn vừa thêm công việc "+request.getTitle()+" cho "+request.getNameEmployee());
        notifications.setEmployee(employee);
        notifications.setDate(LocalDate.now());
        notificationRepository.save(notifications);
    }
    public void deleteTask(int taskId){
        taskRepository.deleteById(taskId);
    }
}
