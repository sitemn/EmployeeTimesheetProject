package com.site.employeetimesheetproject.service;

import com.site.employeetimesheetproject.dto.EmployeeDTO;
import com.site.employeetimesheetproject.dto.ProjectDTO;
import com.site.employeetimesheetproject.model.Employee;
import com.site.employeetimesheetproject.model.Project;
import com.site.employeetimesheetproject.repository.EmployeeRepository;
import com.site.employeetimesheetproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: ProjectService
 * Package: com.site.employeetimesheetproject.service
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public ProjectDTO toProjectDTO(Project project) {
        Set<EmployeeDTO> employeeDTOSet = project.getEmployees().stream()
                .map(employee -> new EmployeeDTO(
                        employee.getId(),
                        employee.getEmployeeName()
                ))
                .collect(Collectors.toSet());

        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                employeeDTOSet
        );
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(String id, Project updatedProject) {
        Optional<Project> projectOptional = projectRepository.findById(id);

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setName(updatedProject.getName());
            project.setDescription(updatedProject.getDescription());
            // Update other fields as needed

            return projectRepository.save(project);
        } else {
            throw new IllegalStateException("Project not found");
        }
    }

    public void deleteProject(String id) {
        Optional<Project> projectOptional = projectRepository.findById(id);

        if (projectOptional.isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Project not found");
        }
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    public Project assignEmployeeToProject(String projectId, String employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalStateException("Project not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));

        project.getEmployees().add(employee);

        projectRepository.save(project);

        return project;
    }

    public boolean isEmployeeAssignedToProject(String employeeId, String projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            return project.getEmployees().stream()
                    .anyMatch(employee -> employee.getId().equals(employeeId));
        }
        return false;
    }
}
