package com.site.employeetimesheetproject.dto;

import com.site.employeetimesheetproject.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: EmployeeDTO
 * Package: com.site.employeetimesheetproject.model.dto
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String id;
    private String employeeName;
//    private String email;
//    private Set<String> roles = new HashSet<>();
}
