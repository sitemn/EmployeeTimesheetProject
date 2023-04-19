package com.site.employeetimesheetproject.service;

import com.site.employeetimesheetproject.dto.EmployeeDTO;
import com.site.employeetimesheetproject.dto.ProjectDTO;
import com.site.employeetimesheetproject.dto.TimesheetDTO;
import com.site.employeetimesheetproject.model.Project;
import com.site.employeetimesheetproject.model.Timesheet;
import com.site.employeetimesheetproject.model.TimeEntry;
import com.site.employeetimesheetproject.repository.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: TimesheetService
 * Package: com.site.employeetimesheetproject.service
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Service
public class TimesheetService {
    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private ProjectService projectService;

    public TimesheetDTO toTimesheetDTO(Timesheet timesheet) {
        return new TimesheetDTO(
                timesheet.getId(),
                timesheet.getEmployee().getId(),
                timesheet.getProject().getId(),
                timesheet.getTimeEntries()
        );
    }

    public Timesheet createTimeEntries(Timesheet timesheet) {
        if (projectService.isEmployeeAssignedToProject(
                timesheet.getEmployee().getId(), timesheet.getProject().getId())) {
            return timesheetRepository.save(timesheet);
        } else {
            throw new IllegalStateException("Employee is not assigned to the project");
        }
    }

    public Timesheet updateTimesheet(Timesheet updatedTimesheet) {
        Timesheet existingTimesheet = timesheetRepository.findById(updatedTimesheet.getId())
                .orElseThrow(() -> new IllegalStateException("Timesheet not found"));

        if (!projectService.isEmployeeAssignedToProject(
                updatedTimesheet.getEmployee().getId(), updatedTimesheet.getProject().getId())) {
            throw new IllegalStateException("Employee is not assigned to the project");
        }

        existingTimesheet.setEmployee(updatedTimesheet.getEmployee());
        existingTimesheet.setProject(updatedTimesheet.getProject());
        existingTimesheet.setTimeEntries(updatedTimesheet.getTimeEntries());
        return timesheetRepository.save(existingTimesheet);
    }

    public void deleteTimesheet(String timesheetId) {
        timesheetRepository.deleteById(timesheetId);
    }

    public List<Timesheet> getTimesheetsByEmployeeIdAndDateRange(String employeeId, LocalDate startDate, LocalDate endDate) {
        return timesheetRepository.findByEmployeeIdAndDateRange(employeeId, startDate, endDate);
    }

    public List<Timesheet> getTimesheetsByProjectIdAndDateRange(String projectId, LocalDate startDate, LocalDate endDate) {
        return timesheetRepository.findByProjectIdAndDateRange(projectId, startDate, endDate);
    }

    public List<Timesheet> getAllTimesheets() {
        return timesheetRepository.findAll();
    }

    public List<Timesheet> getTimesheetsByEmployeeId(String employeeId) {
        return timesheetRepository.findByEmployeeId(employeeId);
    }

    public List<Timesheet> getTimesheetsByProjectId(String projectId) {
        return timesheetRepository.findByProjectId(projectId);
    }

    public Timesheet findTimesheetById(String timesheetId) {
        return timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new IllegalStateException("Timesheet not found"));
    }
}
