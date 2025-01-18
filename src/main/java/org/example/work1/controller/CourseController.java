package org.example.work1.controller;

import org.example.work1.entity.Course;
import org.example.work1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/course-stats")
    public Map<String, Integer> getCourseStats() {
        // 查询总课程数
        long totalCourses = courseRepository.count();

        // 假设 teacherName 唯一标识任课教师，查询任课教师数
        long totalTeachers = courseRepository.findDistinctTeacherIds().size();

        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalCourses", (int) totalCourses);
        stats.put("totalTeachers", (int) totalTeachers);

        return stats;
    }
}