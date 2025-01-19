package org.example.work1.controller;

import org.example.work1.entity.Course;
import org.example.work1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    // 添加获取课程列表的接口
    @GetMapping("/course/list")
    public ResponseEntity<Map<String, Object>> getCourseList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Course> courses = courseRepository.findAll();
            response.put("code", 200);
            response.put("msg", "获取成功");
            response.put("data", courses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "获取课程列表失败");
            return ResponseEntity.ok(response);
        }
    }

    // 添加删除课程的接口
    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            courseRepository.deleteById(id);
            response.put("code", 200);
            response.put("msg", "删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "删除失败");
            return ResponseEntity.ok(response);
        }
    }

    // 添加搜索接口
    @GetMapping("/course/search")
    public ResponseEntity<Map<String, Object>> searchCourses(@RequestParam String keyword) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Course> courses = courseRepository.searchCourses(keyword);
            response.put("code", 200);
            response.put("msg", "搜索成功");
            response.put("data", courses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "搜索失败");
            return ResponseEntity.ok(response);
        }
    }

    // 添加保存课程的接口
    @PostMapping("/course/save")
    public ResponseEntity<Map<String, Object>> saveCourse(@RequestBody Course course) {
        Map<String, Object> response = new HashMap<>();
        try {
            Course savedCourse = courseRepository.save(course);
            response.put("code", 200);
            response.put("msg", "保存成功");
            response.put("data", savedCourse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "保存失败");
            return ResponseEntity.ok(response);
        }
    }
}