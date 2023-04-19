package com.site.employeetimesheetproject.controller;

import com.site.employeetimesheetproject.jwt.JwtUtils;
import com.site.employeetimesheetproject.model.ERole;
import com.site.employeetimesheetproject.model.Role;
import com.site.employeetimesheetproject.model.Employee;
import com.site.employeetimesheetproject.model.EmployeeUserDetails;
import com.site.employeetimesheetproject.payload.JwtResponse;
import com.site.employeetimesheetproject.payload.LoginRequest;
import com.site.employeetimesheetproject.payload.MessageResponse;
import com.site.employeetimesheetproject.payload.SignupRequest;
import com.site.employeetimesheetproject.repository.RoleRepository;
import com.site.employeetimesheetproject.repository.EmployeeRepository;
import com.site.employeetimesheetproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: AuthController
 * Package: com.site.employeetimesheetproject.controller
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateEmployee(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmployeeName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        EmployeeUserDetails userDetails = (EmployeeUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody SignupRequest signUpRequest) {
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

//        // Create new Employee's account
//        Employee employee = new Employee(signUpRequest.getEmployeeName(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> strRoles = signUpRequest.getRoles();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(employeeRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(employeeRole);
//                }
//            });
//        }
//
//        employee.setRoles(roles);
//        employeeRepository.save(employee);

        Employee createdEmployee = employeeService.createEmployee(signUpRequest);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        //return ResponseEntity.ok(new MessageResponse("Employee registered successfully!"));
    }
}