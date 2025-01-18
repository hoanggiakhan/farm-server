package com.farm.farm_manager.service;

import com.farm.farm_manager.dao.AttendanceRepository;
import com.farm.farm_manager.dao.EmployeeRepository;
import com.farm.farm_manager.dto.response.AttendanceResponse;
import com.farm.farm_manager.entity.Attendance;
import com.farm.farm_manager.entity.Employee;
import com.farm.farm_manager.mapper.HandleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AttendanceService {
    AttendanceRepository attendanceRepository;
    EmployeeRepository employeeRepository;

    public List<AttendanceResponse> getAttendanceByEmployee(int userId){
        Employee employee = employeeRepository.findById(userId).orElseThrow();
        List<AttendanceResponse> attendanceResponses = new ArrayList<>();
        for(Attendance attendance : employee.getAttendances()){
            AttendanceResponse attendanceResponse = HandleMapper.INSTANCE.toAttendance(attendance);
            attendanceResponses.add(attendanceResponse);
        }
        return attendanceResponses;
    }
}
