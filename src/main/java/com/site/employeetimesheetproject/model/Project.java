package com.site.employeetimesheetproject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassName: Project
 * Package: com.site.employeetimesheetproject.model
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Getter
@Setter
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private Set<Employee> employees = new HashSet<>();

    public Project() { }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }
}