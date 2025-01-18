package com.farm.farm_manager.controller;

import com.farm.farm_manager.dto.request.EmployeeRequest;
import com.farm.farm_manager.dto.request.LoginRequest;
import com.farm.farm_manager.dto.response.*;
import com.farm.farm_manager.entity.Employee;
import com.farm.farm_manager.service.AttendanceService;
import com.farm.farm_manager.service.employee.EmployeeService;
import com.farm.farm_manager.service.jwt.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class EmployeeController {
    EmployeeService employeeService;
    AuthenticationManager authenticationManager;
    JwtService jwtUtils;
    AttendanceService attendanceService;
    @GetMapping
    List<EmployeeResponse> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate (@RequestBody LoginRequest loginRequest) {
        // Xử lý xác thực người dùng
        try{
            // authentication sẽ giúp ta lấy dữ liệu từ db để kiểm tra
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            // Nếu xác thực thành công
            if (authentication.isAuthenticated()) {

                // Tạo token cho người dùng
                final String jwtToken = jwtUtils.generateToken(loginRequest.getUsername());
                return ResponseEntity.ok(new JwtResponse(jwtToken));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        return ResponseEntity.badRequest().body("Xác thực không thành công");
    }
    @DeleteMapping("/delete-employee/{employeeId}")
    void deleteEmployee(@PathVariable int employeeId){
        employeeService.deleteEmployee(employeeId);
    }
    @GetMapping("/my-info")
    Employee getMyInfo(){
        return employeeService.getMyInfo();
    }

    @PostMapping("/{userId}")
    void createEmployee(@PathVariable int userId , @RequestBody EmployeeRequest request){
        employeeService.createEmployee(userId,request);
    }
    @GetMapping("/notifications/{userId}")
    List<NotificationResponse> getAllNotification(@PathVariable int userId){
      return employeeService.getAllNotification(userId);
    }
    @PutMapping("/read/{idNotification}")
    void NotificationRead(@PathVariable String idNotification){
        employeeService.NotificationRead(idNotification);
    }
    @DeleteMapping("/delete-notifications/{userId}")
    void deleteAllNotification(@PathVariable int userId){
        employeeService.deleteAllNotification(userId);
    }

    @GetMapping("/task/{userId}")
    List<TaskResponse> getAllTask(@PathVariable int userId){
      return employeeService.getAllTaskByEmployee(userId);
    }
    @PutMapping("/task/complete/{taskId}")
    void completeTask(@PathVariable int taskId){
        employeeService.completeTask(taskId);
    }
    @PostMapping("/check-in/{userId}")
    void checkIn( @PathVariable int userId){
      employeeService.checkIn(userId);
    }
    @PutMapping("/check-out/{idAttendance}")
    void checkOut(@PathVariable String idAttendance){
        employeeService.checkOut(idAttendance);
    }
    @GetMapping("/get-attendances/{userId}")
    List<AttendanceResponse> getById(@PathVariable int userId){
        return attendanceService.getAttendanceByEmployee(userId);
    }
    @PutMapping("/update-user/{userId}")
    void updateEmployee(@RequestBody EmployeeRequest request ,@PathVariable int userId){
        employeeService.updateEmployee(request,userId);
    }

    @PutMapping("/total-salary/{userId}")
    void totalSalary(@RequestBody double totalSalary , @PathVariable int userId){
        employeeService.totalSalary(userId,totalSalary);
    }
}
