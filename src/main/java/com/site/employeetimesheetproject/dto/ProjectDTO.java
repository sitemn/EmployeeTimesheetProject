package com.site.employeetimesheetproject.dto;

import com.site.employeetimesheetproject.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: ProjectDTO
 * Package: com.site.employeetimesheetproject.model.dto
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private String id;
    private String name;
    private String description;
    private Set<EmployeeDTO> employees = new HashSet<>();

}
