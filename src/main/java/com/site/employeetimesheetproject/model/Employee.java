package com.site.employeetimesheetproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: Employee
 * Package: com.site.employeetimesheetproject.model
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Getter
@Setter
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;
    @NotBlank
    private String employeeName;
    @NotBlank
    private String email;
    @JsonIgnore
    private String password;
    @DBRef
    private Set<Role> roles = new HashSet<>();

    public Employee() {}

    public Employee(String emplName, String email, String password) {
        this.employeeName = emplName;
        this.email = email;
        this.password = password;
    }

}
