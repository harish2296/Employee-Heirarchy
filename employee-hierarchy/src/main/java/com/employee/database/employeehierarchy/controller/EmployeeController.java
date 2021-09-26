package com.employee.database.employeehierarchy.controller;

import com.employee.database.employeehierarchy.entities.Employee;
import com.employee.database.employeehierarchy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.employee.database.employeehierarchy.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping({"/employee-info"})
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @GetMapping(path="/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> result = new ArrayList<>();
        try {
            result = employeeRepository.findAll();
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer employeeId) {
        Employee result = new Employee();
        try {
            result = employeeRepository.findById(employeeId).get();
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/employees")
    public ResponseEntity<Employee> getEmployeeById(@RequestBody Employee employee) {
        Employee result = new Employee();
        try {
            result = employeeRepository.save(employee);
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path="/employee/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer employeeId,
                                                    @RequestBody Employee employee
                                                    ) {
        Employee result = new Employee();
        try {
            result = employeeRepository.getById(employeeId);
            result.setName(employee.getName());
            result.setManager_id(employee.getManager_id());
            result = employeeRepository.save(employee);
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/employeeAtLevel/{employeeId}")
    public ResponseEntity<List<Employee>> getEmployeeAtLevelById(@PathVariable Integer employeeId) {
        List<Employee> result = new ArrayList<>();
        try {
            result = employeeRepository.getEmployeeAtLevelById(employeeId);
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/employees/{empId}/reportees")
    public ResponseEntity<List<Employee>> getEmployeeListBelowHeirarchy(@PathVariable Integer employeeId) {
        List<Employee> result = new ArrayList<>();
        try {
            result =  employeeService.getDirectAndIndirectRepotees(employeeId);
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/employees/{employeeId}/managers")
    public ResponseEntity<List<Employee>> getEmployeeAboveHeirarchy(@PathVariable Integer employeeId) {
        List<Employee> result = new ArrayList<>();
        try {
            result =  employeeService.getEmployeesCommonManager(employeeId, null, false);
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/employeeCommonSupervisor/{employeeId1}/{employeeId2}")
    public ResponseEntity<Employee> getEmployeeCommonSupervisor(@PathVariable Integer employeeId1, @PathVariable Integer employeeId2) {
        try {
            Employee emp =  employeeService.getEmployeesCommonManager(employeeId1, employeeId2, true).get(0);
            return new ResponseEntity<>(emp, OK);
        } catch (Exception e) {
            System.out.print(e.fillInStackTrace());
            return new ResponseEntity<>(new Employee(), INTERNAL_SERVER_ERROR);
        }
    }
}
