package com.employee.database.employeehierarchy.repositories;

import com.employee.database.employeehierarchy.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "SELECT * from employee emp left join employee e on emp.employee_id = e.manager_id \n" +
            "WHERE emp.employee_id = (SELECT manager_id FROM employee e1 WHERE e1.employee_id = ?1)" ,nativeQuery = true)
    List<Employee> getEmployeeAtLevelById(Integer empId);

    @Query(value = "SELECT * from employee emp left join employee e on emp.employee_id = e.manager_id \n" +
            "WHERE emp.employee_id = (SELECT manager_id FROM employee e1 WHERE e1.employee_id = ?1)" ,nativeQuery = true)
    Employee getEmployeeCommonSupervisor(Integer empid);
}
