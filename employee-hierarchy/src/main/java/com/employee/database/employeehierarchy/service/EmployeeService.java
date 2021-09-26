package com.employee.database.employeehierarchy.service;

import com.employee.database.employeehierarchy.entities.Employee;
import com.employee.database.employeehierarchy.repositories.EmployeeRepository;
import javafx.beans.binding.IntegerBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    BuildEmployeeHeirarchy buildEmployeeHeirarchy;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getDirectAndIndirectRepotees(Integer empId) {
        List<Employee> empList = employeeRepository.findAll();
        Map<Integer, List<Integer>> result =
                buildEmployeeHeirarchy.findRelationship(buildEmployeeHeirarchy.buildEmployeeTree(empList));

        List<Integer> employees = result.get(empId);
        if(employees != null && !employees.isEmpty()) {
            List<Employee> employeeList = new ArrayList<>();
            for(Integer item : employees) {
                if(item != null)
                    employeeList.add(employeeRepository.getById( item));
            }
            return employeeList;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Employee> getEmployeesCommonManager(Integer empId, Integer empId2 , boolean flag) {
        List<Employee> empList = employeeRepository.findAll();
        int low = Integer.MAX_VALUE;
        int key = 0;
        Map<Integer, List<Integer>> result =
                buildEmployeeHeirarchy.findRelationship(buildEmployeeHeirarchy.buildEmployeeTree(empList));
        List<Employee> employeeList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : result.entrySet()) {
            if(flag) {
                if(entry.getValue() != null && entry.getValue().contains(empId) && entry.getValue().contains(empId2)) {
                    employeeList.add(employeeRepository.getById((entry.getKey())));
                    key = low < entry.getValue().size() ? key : entry.getKey();
                    low = low < entry.getValue().size() ? low : entry.getValue().size();

                }

            } else {
                if(entry.getValue() != null && entry.getValue().contains(empId))
                    employeeList.add(employeeRepository.getById( (entry.getKey())));
            }
            System.out.println(entry.getKey() + " —> " + entry.getValue());
        }
        if(flag) {
            employeeList = new ArrayList<>();
            employeeList.add(employeeRepository.getById(key));
        }
        return employeeList;

    }
}
