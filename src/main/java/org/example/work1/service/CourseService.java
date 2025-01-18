package org.example.work1.service;

import org.example.work1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Long getTotalCourses() {
        return courseRepository.countAllCourses();
    }

    public Long getTotalTeachers() {
        return courseRepository.countTeachers();
    }
}