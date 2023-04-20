package com.site.employeetimesheetproject.controller;

import com.site.employeetimesheetproject.dto.ProjectDTO;
import com.site.employeetimesheetproject.model.Employee;
import com.site.employeetimesheetproject.model.Project;
import com.site.employeetimesheetproject.payload.MessageResponse;
import com.site.employeetimesheetproject.payload.SignupRequest;
import com.site.employeetimesheetproject.repository.EmployeeRepository;
import com.site.employeetimesheetproject.service.EmployeeService;
import com.site.employeetimesheetproject.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: EmployeeController
 * Package: com.site.employeetimesheetproject.controller
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "This endpoint handles POST requests to create a new employee",
            notes = "It requires the user to have an ADMIN role to access it. ")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody SignupRequest signUpRequest) {
        if (employeeRepository.existsByEmployeeName(signUpRequest.getEmployeeName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Employee name is already taken!"));
        }

        if (employeeRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Employee createdEmployee = employeeService.createEmployee(signUpRequest);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint handles PUT requests to update an existing employee with the given id",
            notes = "The user must either have an ADMIN role or be the owner of the employee record to access this endpoint. ")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or principal.id.equals(#id)")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        try {
            Employee updated = employeeService.updateEmployee(id, updatedEmployee);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
//        try {
//            Employee updated = employeeService.updateEmployee(id, updatedEmployee);
//            EmployeeDTO employeeDTO = toEmployeeDTO(updated);
//            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
//        } catch (IllegalStateException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @ApiOperation(value = "This endpoint handles DELETE requests to delete an existing employee with the given id.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "This endpoint handles GET request to retrieve all existing employees.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
//        List<Employee> employees = employeeService.getAllEmployees();
//        List<EmployeeDTO> employeeDTOs = employees.stream()
//                .map(this::toEmployeeDTO)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
//    }
    @ApiOperation(value = "This endpoint handles GET requests to get an employee with the given id. ",
            notes = "The user must either have an ADMIN role or be the owner of the employee record to access this endpoint. ")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or principal.id.equals(#id)")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
//        return employeeService.getEmployeeById(id)
//                .map(employee -> {
//                    EmployeeDTO employeeDTO = toEmployeeDTO(employee);
//                    return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
//                })
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @ApiOperation(value = "This endpoint handles GET requests to get a list of projects associated with an employee with the given id.",
            notes = "The user must either have an ADMIN role or be the owner of the employee record to access this endpoint. ")
    @GetMapping("/{id}/projects")
    @PreAuthorize("hasRole('ADMIN') or principal.id.equals(#id)")
    public ResponseEntity<List<ProjectDTO>> getProjectsByEmployeeId(@PathVariable String id) {
        List<Project> projects = employeeService.findProjectsByEmployeeId(id);
        List<ProjectDTO> projectDTOS = projects.stream()
                .map(projectService::toProjectDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projectDTOS, HttpStatus.OK);
    }
}