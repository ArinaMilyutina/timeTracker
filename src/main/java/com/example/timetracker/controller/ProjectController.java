package com.example.timetracker.controller;


import com.example.timetracker.entity.Project;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.jwt.util.JwtUtil;
import com.example.timetracker.service.ProjectService;
import com.example.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private JwtUtil jwtUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentAdminUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
            User currentAdmin = userService.findByUsername(currentAdminUsername);
            project.setAdmin(currentAdmin);
            Project createdProject = projectService.saveProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating project: " + e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{name}")
    public ResponseEntity<Project> updateProject(@PathVariable String name, @RequestBody Project updateProject) {
        Project updateResponseProject = projectService.updateProjectByName(name, updateProject);
        return ResponseEntity.ok(updateResponseProject);

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProject(@PathVariable String name, @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        String currentAdminId = jwtUtil.extractUserId(token);

        Project project = projectService.findByName(name);

        if (project != null && project.getAdmin().equals(currentAdminId)) {
            projectService.delete(project);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
