package org.example.work1.service.impl;

import org.example.work1.entity.Student;
import org.example.work1.repository.StudentRepository;
import org.example.work1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public long getTotalStudentCount() {
        return studentRepository.count();
    }

    @Override
    public long getMaleStudentCount() {
        return studentRepository.countBySex(1); // Assuming 1 represents male
    }

    @Override
    public long getFemaleStudentCount() {
        return studentRepository.countBySex(0); // Assuming 2 represents female
    }

    @Override
    public Map<String, Long> getCollegeDistribution() {
        List<Object[]> results = studentRepository.getCollegeCounts();
        Map<String, Long> collegeData = new HashMap<>();
        for (Object[] result : results) {
            String college = (String) result[0];
            Long count = (Long) result[1];
            collegeData.put(college, count);
        }
        return collegeData;
    }
}
