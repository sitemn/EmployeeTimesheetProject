package com.site.employeetimesheetproject.controller;

import com.site.employeetimesheetproject.dto.ProjectDTO;
import com.site.employeetimesheetproject.dto.TimesheetDTO;
import com.site.employeetimesheetproject.model.Timesheet;
import com.site.employeetimesheetproject.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: TimesheetController
 * Package: com.site.employeetimesheetproject.controller
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @PostMapping
    public ResponseEntity<Timesheet> createTimeEntries(@RequestBody Timesheet timesheet) {
        Timesheet createdTimesheet = timesheetService.createTimeEntries(timesheet);
        return new ResponseEntity<>(createdTimesheet, HttpStatus.CREATED);
    }

    @PutMapping("/{timesheetId}")
    public ResponseEntity<Timesheet> updateTimesheet(@PathVariable String timesheetId, @RequestBody Timesheet updatedTimesheet) {
        updatedTimesheet.setId(timesheetId);
        Timesheet updated = timesheetService.updateTimesheet(updatedTimesheet);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{timesheetId}")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable String timesheetId) {
        timesheetService.deleteTimesheet(timesheetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TimesheetDTO>> getAllTimesheets() {
        List<Timesheet> timesheets = timesheetService.getAllTimesheets();
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TimesheetDTO>> getTimesheetsByEmployeeId(@PathVariable String employeeId) {
        List<Timesheet> timesheets = timesheetService.getTimesheetsByEmployeeId(employeeId);
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TimesheetDTO>> getTimesheetsByProjectId(@PathVariable String projectId) {
        List<Timesheet> timesheets = timesheetService.getTimesheetsByProjectId(projectId);
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}/date-range")
    public ResponseEntity<List<TimesheetDTO>> getTimesheetsByEmployeeIdAndDateRange(
            @PathVariable String employeeId,
            @RequestParam("startDate") String startDateString,
            @RequestParam("endDate") String endDateString) {
        LocalDate startDate = LocalDate.parse(startDateString, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateString, dateFormatter);
        //System.out.println(startDate);
        List<Timesheet> timesheets = timesheetService.getTimesheetsByEmployeeIdAndDateRange(employeeId, startDate, endDate);
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}/date-range")
    public ResponseEntity<List<TimesheetDTO>> getTimesheetsByProjectIdAndDateRange(
            @PathVariable String projectId,
            @RequestParam("startDate") String startDateString,
            @RequestParam("endDate") String endDateString) {
        LocalDate startDate = LocalDate.parse(startDateString, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateString, dateFormatter);

        List<Timesheet> timesheets = timesheetService.getTimesheetsByProjectIdAndDateRange(projectId, startDate, endDate);
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @GetMapping("/{timesheetId}")
    public ResponseEntity<Timesheet> getTimesheetById(@PathVariable String timesheetId) {
        Timesheet timesheet = timesheetService.findTimesheetById(timesheetId);
        return new ResponseEntity<>(timesheet, HttpStatus.OK);
    }
}
