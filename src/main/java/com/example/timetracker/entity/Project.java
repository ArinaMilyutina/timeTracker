package com.example.timetracker.entity;

import com.example.timetracker.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}
