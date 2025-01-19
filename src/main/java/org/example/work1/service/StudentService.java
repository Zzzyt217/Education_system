package org.example.work1.service;

import org.example.work1.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> getAllStudents();
    long getTotalStudentCount();
    long getMaleStudentCount();
    long getFemaleStudentCount();
    Map<String, Long> getCollegeDistribution();
}
