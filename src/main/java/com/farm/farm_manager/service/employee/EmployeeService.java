package com.farm.farm_manager.service.employee;

import com.farm.farm_manager.dao.*;
import com.farm.farm_manager.dto.request.EmployeeRequest;
import com.farm.farm_manager.dto.response.AttendanceResponse;
import com.farm.farm_manager.dto.response.EmployeeResponse;
import com.farm.farm_manager.dto.response.NotificationResponse;
import com.farm.farm_manager.dto.response.TaskResponse;
import com.farm.farm_manager.entity.*;
import com.farm.farm_manager.mapper.HandleMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class EmployeeService {
     EmployeeRepository employeeRepository;
     AttendanceRepository attendanceRepository;
     FarmRepository farmRepository;
     PasswordEncoder passwordEncoder;
     NotificationRepository notificationRepository;
     TaskRepository taskRepository;
    public List<EmployeeResponse> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponse employeeResponse = HandleMapper.INSTANCE.toEmployee(employee);
            employeeResponses.add(employeeResponse);
        }

        return employeeResponses;
    }


    public void deleteEmployee(int employeeId){
        employeeRepository.deleteById(employeeId);
    }
    public Employee getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Employee employee = employeeRepository.findByUsername(username);
        return employee;
    }

    public void createEmployee(int userId , EmployeeRequest request){
       Employee employee = employeeRepository.findById(userId).orElseThrow();
        Farm farm = farmRepository.findById(employee.getFarm().getFarmId()).orElseThrow();

        if(employeeRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("username đã tồn tại");
        }
        Employee newEmployee = HandleMapper.INSTANCE.toEmployeeRequest(request);
        newEmployee.setFullName(request.getFullName());
        newEmployee.setFarm(farm);
        newEmployee.setRole("EMPLOYEE");
        newEmployee.setPassword(passwordEncoder.encode(request.getPassword()));
        Notifications notifications = new Notifications();
        notifications.setDate(LocalDate.now());
        notifications.setContent("Bạn vừa thêm 1 nhân viên với chức vụ là Nhân viên");
        notifications.setStatus(0);
        notifications.setEmployee(employee);
        notificationRepository.save(notifications);
        employeeRepository.save(newEmployee);

    }
    public List<NotificationResponse> getAllNotification(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for(Notifications notification : employee.getNotifications()){
            NotificationResponse notificationResponse = HandleMapper.INSTANCE.toNotifications(notification);
            notificationResponses.add(notificationResponse);
        }
        return notificationResponses;
    }
    @Transactional
    public void NotificationRead(String idNotification){
        Notifications notifications = notificationRepository.findById(idNotification).orElseThrow();
        notifications.setStatus(1);
        notificationRepository.saveAndFlush(notifications);
    }
   @Transactional
    public void deleteAllNotification(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        notificationRepository.deleteByEmployee(employee);
        employee.getNotifications().clear();
    }

    public List<TaskResponse> getAllTaskByEmployee(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        List<TaskResponse> taskResponses = new ArrayList<>();
        for(Task task : employee.getTasks()){
            TaskResponse taskResponse = HandleMapper.INSTANCE.toTask(task);
            taskResponse.setNameEmployee(employee.getFullName());
            if(task.getCrop()==null && task.getAnimal()!=null){
                taskResponse.setAnimalName(task.getAnimal().getAnimalName());
            }
            if(task.getAnimal()==null && task.getCrop()!=null){
                taskResponse.setCropName(task.getCrop().getCropName());
            }
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    }

    public void completeTask(int taskId){
        Task task = taskRepository.findById(taskId).orElseThrow();
        task.setStatus(1);
        taskRepository.saveAndFlush(task);
    }

    public void checkIn(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.now());
        attendance.setCheckInTime(LocalTime.now());
        attendance.setEmployee(employee);
        attendanceRepository.save(attendance);
    }
    @Transactional
    public void checkOut(String idAttendance){
       Attendance attendance = attendanceRepository.findById(idAttendance).orElseThrow();
       attendance.setCheckOutTime(LocalTime.now());
       LocalTime start = LocalTime.of(attendance.getCheckInTime().getHour(),attendance.getCheckInTime().getMinute());
       LocalTime end = LocalTime.of(LocalTime.now().getHour(),LocalTime.now().getMinute());
        Duration duration = Duration.between(start, end);
        double hours = duration.toMinutes() / 60.0;
        double totalMerits = hours / 8;
       // Định dạng đến 2 chữ số thập phân
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedTotalMerits = df.format(totalMerits);

// Nếu cần lưu lại dưới dạng số thực
        attendance.setTotalMerits(Double.parseDouble(formattedTotalMerits));
        attendanceRepository.saveAndFlush(attendance);
    }
   public AttendanceResponse getById(String id){
        Attendance attendance = attendanceRepository.findById(id).orElseThrow();
        AttendanceResponse attendanceResponse = HandleMapper.INSTANCE.toAttendance(attendance);
        return attendanceResponse;
    }

    public void updateEmployee(EmployeeRequest request, int userId) {
        Employee employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + userId));
        if(!request.getPassword().equals(employee.getPassword())){
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        // Cập nhật thông tin nhân viên từ request, ngoại trừ mật khẩu
        HandleMapper.INSTANCE.updateEmployeeFromRequest(request, employee);
        employeeRepository.saveAndFlush(employee);
    }

    public void totalSalary(int userId ,double total){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        employee.setSalary(total);
        employeeRepository.saveAndFlush(employee);
    }

}
