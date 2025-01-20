package org.example.work1.entity;

import javax.persistence.*;

@Entity
@Table(name = "SC")
public class SC {
    @Id
    @Column(name = "courseName")
    private String courseName;

    @Column(name = "username")
    private String username;

    @Column(name = "courseTime")
    private String courseTime;

    @Column(name = "classLocation")
    private String classLocation;

    // Getters and Setters
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

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }
} 