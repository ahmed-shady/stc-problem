package com.aboshady.leave.controller;


import com.aboshady.leave.model.EmployeeLeavesResponse;
import com.aboshady.leave.repository.EmployeeRepository;
import com.aboshady.leave.model.Employee;
import com.aboshady.leave.model.Leave;
import com.aboshady.leave.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping
public class LeaveController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    LeaveRepository leaveRepository;


    @GetMapping("/{id}")
    public ResponseEntity<Leave> getLeave(@PathVariable("id") Long leaveId) {
        Optional<Leave> leaveData = leaveRepository.findById(leaveId);
        if (leaveData.isPresent()) {
            return new ResponseEntity<>(leaveData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/{employee-id}")
    public ResponseEntity<EmployeeLeavesResponse> getLeaves(@PathVariable("employee-id") Long employeeId) {
        Optional<Employee> employeeData = employeeRepository.findById(employeeId);
        if (!employeeData.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Employee employee = employeeData.get();
        List<Leave> leaves = leaveRepository.findByEmployee(employee);
        return new ResponseEntity<>(new EmployeeLeavesResponse(employeeId, employee.getName(), leaves), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leave> putLeave(@PathVariable("id") Long leaveId, @RequestBody Leave newLeave) {
        Optional<Leave> leaveData = leaveRepository.findById(leaveId);
        if (leaveData.isPresent()) {
            Leave leave = leaveData.get();
            leave.setType(newLeave.getType());
            leave.setLeaveDate(newLeave.getLeaveDate());
            return new ResponseEntity<>(leaveRepository.save(leave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Leave> deleteLeave(@PathVariable("id") Long id) {
        try {
            leaveRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employee/{employee-id}")
    public ResponseEntity<Leave> postLeave(@PathVariable("employee-id") Long employeeId,@RequestBody Leave leave) {
        try {
            Optional<Employee> employeeData = employeeRepository.findById(employeeId);
            if(employeeData.isPresent()) {
                Employee employee = employeeData.get();
                leave.setEmployee(employee);

                return new ResponseEntity<>(leaveRepository.save(leave), HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
