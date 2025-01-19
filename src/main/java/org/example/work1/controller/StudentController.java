package org.example.work1.controller;

import org.example.work1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/stats")
    public StudentStats getStudentStats() {
        long total = studentService.getTotalStudentCount();
        long male = studentService.getMaleStudentCount();
        long female = studentService.getFemaleStudentCount();
        Map<String, Long> collegeDistribution = studentService.getCollegeDistribution();

        return new StudentStats(total, male, female, collegeDistribution);
    }
}

class StudentStats {
    private long total;
    private long male;
    private long female;
    private Map<String, Long> collegeDistribution;

    public StudentStats(long total, long male, long female, Map<String, Long> collegeDistribution) {
        this.total = total;
        this.male = male;
        this.female = female;
        this.collegeDistribution = collegeDistribution;
    }

    public long getTotal() {
        return total;
    }

    public long getMale() {
        return male;
    }

    public long getFemale() {
        return female;
    }

    public Map<String, Long> getCollegeDistribution() {
        return collegeDistribution;
    }
}
