package org.example.work1.entity;

import javax.persistence.*;

@Entity
@Table(name = "SC")
public class SC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "username")
    private String username;

    @Column(name = "coursetime")
    private String coursetime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoursetime() {
        return coursetime;
    }

    public void setCoursetime(String coursetime) {
        this.coursetime = coursetime;
    }
} 