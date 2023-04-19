package com.site.employeetimesheetproject.service;

import com.site.employeetimesheetproject.dto.EmployeeDTO;
import com.site.employeetimesheetproject.model.ERole;
import com.site.employeetimesheetproject.model.Employee;
import com.site.employeetimesheetproject.model.Project;
import com.site.employeetimesheetproject.model.Role;
import com.site.employeetimesheetproject.payload.SignupRequest;
import com.site.employeetimesheetproject.repository.EmployeeRepository;
import com.site.employeetimesheetproject.repository.ProjectRepository;
import com.site.employeetimesheetproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ProjectRepository projectRepository;

    private EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        return employeeDTO;
    }

    public Employee createEmployee(SignupRequest signUpRequest) {
        // Create new Employee's account
        Employee employee = new Employee(signUpRequest.getEmployeeName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(employeeRole);
                }
            });
        }

        employee.setRoles(roles);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(String id, Employee updatedEmployee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setEmployeeName(updatedEmployee.getEmployeeName());
            employee.setEmail(updatedEmployee.getEmail());
//            // Check if the password has changed, and if so, encrypt it
//            if (!employee.getPassword().equals(updatedEmployee.getPassword())) {
//                employee.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
//            }

            return employeeRepository.save(employee);
        } else {
            throw new IllegalStateException("Employee not found");
        }
    }

    public void deleteEmployee(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Employee not found");
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public List<Project> findProjectsByEmployeeId(String employeeId) {
        return projectRepository.findByEmployeeId(employeeId);
    }
}
