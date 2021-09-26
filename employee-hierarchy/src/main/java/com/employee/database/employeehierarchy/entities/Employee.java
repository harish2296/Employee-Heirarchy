package com.employee.database.employeehierarchy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Table(name="employee")
@Entity

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_id")
    public int employee_id;

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", name='" + name + '\'' +
//                ", manager=" + manager +
//                ", subordinates=" + subordinates+
                '}';
    }

    public String name;

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public int manager_id;

//    public Employee getManager() {
//        return manager;
//    }
//
//    public void setManager(Employee manager) {
//        this.manager = manager;
//    }
//
//    public Set<Employee> getSubordinates() {
//        return subordinates;
//    }
//
//    public void setSubordinates(Set<Employee> subordinates) {
//        this.subordinates = subordinates;
//    }

//    @ManyToOne(cascade={CascadeType.ALL})
//    @JoinColumn(name="manager_id")
//    @JsonIgnore
//    private Employee manager;
//
//    @OneToMany(mappedBy="manager")
//    @JsonIgnore
//    public Set<Employee> subordinates = new HashSet<Employee>();


}
