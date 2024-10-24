package com.example.timetracker.service;


import com.example.timetracker.entity.Project;
import com.example.timetracker.repository.ProjectRepository;
import com.example.timetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}
