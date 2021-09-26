package com.employee.database.employeehierarchy.service;

import com.employee.database.employeehierarchy.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BuildEmployeeHeirarchy {

    public Map<Integer, Integer> buildEmployeeTree(List<Employee> employees) {
        Map<Integer, Integer> employeeTree = new HashMap<>();
        for (Employee employee : employees) {
            employeeTree.put(employee.getEmployee_id(), employee.getManager_id());
        }
        return employeeTree;
    }

    public Map<Integer, List<Integer>> findRelationship(Map<Integer, Integer> employeeMap) {
        Map<Integer, List<Integer>> topDownMap = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : employeeMap.entrySet()) {
            int employee = entry.getKey();
            int manager = entry.getValue();
            if (employee != manager) {
                topDownMap.putIfAbsent(manager, new ArrayList<>());
                topDownMap.get(manager).add(employee);
            }
        }

        Map<Integer, List<Integer>> result = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : employeeMap.entrySet()) {
            getIndirectRepotees(entry.getKey(), topDownMap,
                    result);
        }
        return result;
    }

    public List<Integer> getIndirectRepotees(Integer manager,
                                             Map<Integer, List<Integer>> topDownMap,
                                             Map<Integer, List<Integer>> result) {

        if (result.containsKey(manager)) {
            return result.get(manager);
        }


        List<Integer> managerEmployees = topDownMap.get(manager);

        if (managerEmployees != null) {
            for (Integer reportee : new ArrayList<>(managerEmployees)) {
                List<Integer> employees = getIndirectRepotees(reportee,
                        topDownMap, result);
                if (employees != null) {
                    managerEmployees.addAll(employees);
                }
            }
        }
        result.put(manager, managerEmployees);
        return managerEmployees;
    }
}
