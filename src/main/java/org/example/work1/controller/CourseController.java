package org.example.work1.controller;

import org.example.work1.entity.Course;
import org.example.work1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/stats")
    public Map<String, Object> getCourseStats() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 查询总课程数
            long totalCourses = courseRepository.count();
            // 查询任课教师数
            long totalTeachers = courseRepository.findDistinctTeacherIds().size();

            result.put("code", 200);
            result.put("msg", "success");
            result.put("totalCourses", (int) totalCourses);
            result.put("totalTeachers", (int) totalTeachers);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "获取统计信息失败");
        }
        return result;
    }

    // 获取单个课程信息
    @GetMapping("/{id}")
    public Map<String, Object> getCourseById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Course course = courseRepository.findById(id).orElse(null);
            if (course != null) {
                result.put("code", 200);
                result.put("msg", "success");
                result.put("data", course);
            } else {
                result.put("code", 400);
                result.put("msg", "课程不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "获取课程信息失败");
        }
        return result;
    }

    // 更新课程信息
    @PutMapping("/update")
    public Map<String, Object> updateCourse(@RequestBody Course course) {
        Map<String, Object> result = new HashMap<>();
        try {
            courseRepository.save(course);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "更新失败");
        }
        return result;
    }

    // 获取课程列表
    @GetMapping("/list")
    public Map<String, Object> getCourseList() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Course> courses = courseRepository.findAll();
            result.put("code", 200);
            result.put("msg", "success");
            result.put("data", courses);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "获取课程列表失败");
        }
        return result;
    }

    // 添加删除课程的接口
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 先获取要删除的课程信息
            Course course = courseRepository.findById(id).orElse(null);
            if (course == null) {
                response.put("code", 400);
                response.put("msg", "课程不存在");
                return ResponseEntity.ok(response);
            }

            // 先删除选课表中的关联记录
            String courseName = course.getCourseName();
            jdbcTemplate.update("DELETE FROM SC WHERE courseName = ?", courseName);

            // 然后删除课程
            courseRepository.deleteById(id);
            
            response.put("code", 200);
            response.put("msg", "删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 500);
            response.put("msg", "删除失败: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 添加搜索接口
    @GetMapping("/search")
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
    @PostMapping("/save")
    public Map<String, Object> saveCourse(@RequestBody Course course) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("接收到的课程数据: " + course);
            courseRepository.save(course);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 添加课程状态统计方法
    @GetMapping("/status-stats")
    public Map<String, Object> getCourseStatusStats() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 查询待确认课程数量（status = 0）
            long pendingCourses = courseRepository.countByCourseStatus(0);
            // 查询已确认课程数量（status = 1）
            long confirmedCourses = courseRepository.countByCourseStatus(1);

            result.put("code", 200);
            result.put("msg", "success");
            result.put("pendingCourses", pendingCourses);
            result.put("confirmedCourses", confirmedCourses);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "获取统计信息失败");
        }
        return result;
    }

    // 获取待确认课程列表
    @GetMapping("/pending")
    public Map<String, Object> getPendingCourses() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 使用JPQL查询来获取待确认课程
            List<Course> pendingCourses = courseRepository.findPendingCourses();
            result.put("code", 200);
            result.put("msg", "success");
            result.put("data", pendingCourses);
        } catch (Exception e) {
            e.printStackTrace(); // 添加错误日志
            result.put("code", 500);
            result.put("msg", "获取待确认课程失败: " + e.getMessage());
        }
        return result;
    }

    // 确认单个课程
    @PutMapping("/confirm/{id}")
    public Map<String, Object> confirmCourse(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Course course = courseRepository.findById(id).orElse(null);
            if (course != null) {
                course.setCourseStatus(1); // 设置为已确认
                courseRepository.save(course);
                result.put("code", 200);
                result.put("msg", "success");
            } else {
                result.put("code", 400);
                result.put("msg", "课程不存在");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 添加错误日志
            result.put("code", 500);
            result.put("msg", "确认失败: " + e.getMessage());
        }
        return result;
    }

    // 批量确认课程
    @PutMapping("/confirm-batch")
    public Map<String, Object> confirmCourseBatch(@RequestBody List<Long> ids) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Course> courses = courseRepository.findAllById(ids);
            courses.forEach(course -> course.setCourseStatus(1));
            courseRepository.saveAll(courses);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "批量确认失败");
        }
        return result;
    }
}