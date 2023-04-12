package com.site.employeetimesheetproject.repository;

import com.site.employeetimesheetproject.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * ClassName: EmployeeRepository
 * Package: com.site.employeetimesheetproject.repository
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Boolean existsByEmployeeName(String employeeName);

    Boolean existsByEmail(String email);

    Optional<Employee> findByEmployeeName(String employeeName);
}