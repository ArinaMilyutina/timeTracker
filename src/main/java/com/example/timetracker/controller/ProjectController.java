package com.example.timetracker.controller;


import com.example.timetracker.entity.Project;
import com.example.timetracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            Project createdProject = projectService.saveProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating project: " + e.getMessage());
        }
    }

}
