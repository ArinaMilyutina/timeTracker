package com.example.timetracker.controller;

import com.example.timetracker.entity.Project;
import com.example.timetracker.entity.Task;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.service.ProjectService;
import com.example.timetracker.service.TaskService;
import com.example.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{projectId}")
    public ResponseEntity<?> createTask(@PathVariable Long projectId, @RequestBody Task task) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(username);

        Optional<Project> project = projectService.findById(projectId);

        if (project.isPresent()) {
            Project project1 = project.get();
            task.setProject(project1);
            task.setAdmin(currentUser);
            Task savedTask = taskService.saveTask(task);

            return ResponseEntity.ok(savedTask);
        }
        return ResponseEntity.badRequest().body("Проект не найден");

    }
}

