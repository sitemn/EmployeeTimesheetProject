package com.site.employeetimesheetproject.controller;

import com.site.employeetimesheetproject.dto.EmployeeDTO;
import com.site.employeetimesheetproject.dto.ProjectDTO;
import com.site.employeetimesheetproject.model.Project;
import com.site.employeetimesheetproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
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

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        ProjectDTO projectDTO = projectService.toProjectDTO(createdProject);
        return new ResponseEntity<>(projectDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id, @RequestBody Project updatedProject) {
        try {
            Project updated = projectService.updateProject(id, updatedProject);
            ProjectDTO projectDTO = projectService.toProjectDTO(updated);
            return new ResponseEntity<>(projectDTO, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> projectDTOS = projects.stream()
                .map(projectService::toProjectDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projectDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id)
                .map(project -> {
                    ProjectDTO projectDTO = projectService.toProjectDTO(project);
                    return new ResponseEntity<>(projectDTO, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{projectId}/assign-employee/{employeeId}")
    public ResponseEntity<ProjectDTO> assignEmployeeToProject(
            @PathVariable String projectId, @PathVariable String employeeId) {
        Project updatedProject = projectService.assignEmployeeToProject(projectId, employeeId);
        ProjectDTO projectDTO = projectService.toProjectDTO(updatedProject);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }
}