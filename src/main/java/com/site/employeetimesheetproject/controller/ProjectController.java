package com.site.employeetimesheetproject.controller;

import com.site.employeetimesheetproject.dto.ProjectDTO;
import com.site.employeetimesheetproject.model.Project;
import com.site.employeetimesheetproject.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ProjectController
 * Package: com.site.employeetimesheetproject.controller
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "This endpoint handles POST requests to create a new project.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        ProjectDTO projectDTO = projectService.toProjectDTO(createdProject);
        return new ResponseEntity<>(projectDTO, HttpStatus.CREATED);
    }

    @ApiOperation(value = "This endpoint handles PUT requests to update an existing project with the given id.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id, @RequestBody Project updatedProject) {
        try {
            Project updated = projectService.updateProject(id, updatedProject);
            ProjectDTO projectDTO = projectService.toProjectDTO(updated);
            return new ResponseEntity<>(projectDTO, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "This endpoint handles DELETE requests to delete an existing project with the given id",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "This endpoint handles GET requests to retrieve all projects.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> projectDTOS = projects.stream()
                .map(projectService::toProjectDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projectDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "This endpoint handles GET requests to get an existing project with the given id.",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id)
                .map(project -> {
                    ProjectDTO projectDTO = projectService.toProjectDTO(project);
                    return new ResponseEntity<>(projectDTO, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "This endpoint handles POST requests to assign " +
            "an employee with the given employeeId to a project with the given projectId. ",
            notes = "The user must have an ADMIN role to access this endpoint. ")
    @PostMapping("/{projectId}/assign-employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> assignEmployeeToProject(
            @PathVariable String projectId, @PathVariable String employeeId) {
        Project updatedProject = projectService.assignEmployeeToProject(projectId, employeeId);
        ProjectDTO projectDTO = projectService.toProjectDTO(updatedProject);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }
}