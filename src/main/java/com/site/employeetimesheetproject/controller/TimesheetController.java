package com.site.employeetimesheetproject.controller;

import com.site.employeetimesheetproject.dto.TimesheetDTO;
import com.site.employeetimesheetproject.model.Timesheet;
import com.site.employeetimesheetproject.service.TimesheetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ApiOperation(value = "This endpoint handles POST requests to create a new Timesheet.",
            notes = "by taking a Timesheet object as input and returning the created timesheet as the response")
    @PostMapping
    public ResponseEntity<Timesheet> createTimeEntries(@RequestBody Timesheet timesheet) {
        Timesheet createdTimesheet = timesheetService.createTimeEntries(timesheet);
        return new ResponseEntity<>(createdTimesheet, HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint handles PUT requests to update a new Timesheet.",
            notes = "by taking a timesheet ID and a Timesheet object as input, and returning the updated timesheet as the response")
    @PutMapping("/{timesheetId}")
    public ResponseEntity<Timesheet> updateTimesheet(@PathVariable String timesheetId, @RequestBody Timesheet updatedTimesheet) {
        updatedTimesheet.setId(timesheetId);
        Timesheet updated = timesheetService.updateTimesheet(updatedTimesheet);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint handles DELETE requests to deletes an existing timesheet ",
            notes = "by taking a timesheet ID as input and returning a success status as the response.")
    @DeleteMapping("/{timesheetId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable String timesheetId) {
        timesheetService.deleteTimesheet(timesheetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "This endpoint handles GET requests to retrieve all Timesheets.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TimesheetDTO>> getAllTimesheets() {
        List<Timesheet> timesheets = timesheetService.getAllTimesheets();
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint handles GET requests to retrieve timesheets for a specific employee.",
            notes = "by taking the employee ID as input and returning them as a list of TimesheetDTO objects.")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN') or principal.id.equals(#employeeId)")
    public ResponseEntity<List<TimesheetDTO>> getTimesheetsByEmployeeId(@PathVariable String employeeId) {
        List<Timesheet> timesheets = timesheetService.getTimesheetsByEmployeeId(employeeId);
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint handles GET requests to retrieve timesheets for a specific project.",
            notes = "by taking the project ID as input and returning them as a list of TimesheetDTO objects.")
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TimesheetDTO>> getTimesheetsByProjectId(@PathVariable String projectId) {
        List<Timesheet> timesheets = timesheetService.getTimesheetsByProjectId(projectId);
        List<TimesheetDTO> timesheetDTOS = timesheets.stream()
                .map(timesheetService::toTimesheetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(timesheetDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint handles GET requests to retrieve timesheets for a specific employee within a given date range ",
            notes = "by taking the employee ID, the start date, and the end date as input and returning them as a list of TimesheetDTO objects")
    @GetMapping("/employee/{employeeId}/date-range")
    @PreAuthorize("hasRole('ADMIN') or principal.id.equals(#employeeId)")
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

    @ApiOperation(value = "This endpoint handles GET requests to retrieve timesheets for a specific project within a given date range ",
            notes = "by taking the project ID, the start date, and the end date as input and returning them as a list of TimesheetDTO objects")
    @GetMapping("/project/{projectId}/date-range")
    @PreAuthorize("hasRole('ADMIN')")
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

    @ApiOperation(value = "This endpoint handles GET requests to get a Timesheet with given ID.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @GetMapping("/{timesheetId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Timesheet> getTimesheetById(@PathVariable String timesheetId) {
        Timesheet timesheet = timesheetService.findTimesheetById(timesheetId);
        return new ResponseEntity<>(timesheet, HttpStatus.OK);
    }
}
