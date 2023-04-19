package com.site.employeetimesheetproject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: Timesheet
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Getter
@Setter
@Document(collection = "timesheets")
public class Timesheet {
    @Id
    private String id;
    @DBRef
    private Project project;
    @DBRef
    private Employee employee;

    private List<TimeEntry> timeEntries = new ArrayList<>();

    public Timesheet() {
    }

    public Timesheet(Project project, Employee employee, List<TimeEntry> timeEntries) {
        this.project = project;
        this.employee = employee;
        this.timeEntries = timeEntries;
    }
}
