package com.example.timetracker.service;

import com.example.timetracker.entity.Task;
import com.example.timetracker.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TasksRepository tasksRepository;

    public Task saveTask(Task task) {
        return tasksRepository.save(task);
    }
}
