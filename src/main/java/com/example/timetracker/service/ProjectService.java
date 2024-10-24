package com.example.timetracker.service;


import com.example.timetracker.entity.Project;
import com.example.timetracker.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProjectByName(String name, Project updateProject) {
        Project existingProject = projectRepository.findByName(name);
        if (existingProject != null) {
            existingProject.setName(updateProject.getName());
            existingProject.setDescription(updateProject.getDescription());
            existingProject.setStartDate(updateProject.getStartDate());
            existingProject.setEndDate(updateProject.getEndDate());
            return projectRepository.save(existingProject);
        }
        return null;
    }

    public Project findByName(String name) {
        return projectRepository.findByName(name);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public Optional<Project> findById(Long projectId) {
        return projectRepository.findById(projectId);
    }
}
