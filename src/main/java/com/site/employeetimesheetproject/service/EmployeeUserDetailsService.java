package com.site.employeetimesheetproject.service;

import com.site.employeetimesheetproject.model.Employee;
import com.site.employeetimesheetproject.model.EmployeeUserDetails;
import com.site.employeetimesheetproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: UserDetailsService
 * Package: com.site.employeetimesheetproject.service
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Service
public class EmployeeUserDetailsService implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmployeeName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return EmployeeUserDetails.build(employee);
    }

}