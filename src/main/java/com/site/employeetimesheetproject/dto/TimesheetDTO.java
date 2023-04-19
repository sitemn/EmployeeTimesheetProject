package com.site.employeetimesheetproject.dto;

import com.site.employeetimesheetproject.model.Employee;
import com.site.employeetimesheetproject.model.Project;
import com.site.employeetimesheetproject.model.TimeEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: TimesheetDTO
 * Package: com.site.employeetimesheetproject.model.dto
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetDTO {
    private String id;
    private String employeeId;
    private String projectId;
    private List<TimeEntry> timeEntries = new ArrayList<>();
}
