package org.example.work1.controller;

import org.example.work1.entity.Teacher;
import org.example.work1.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/api/teacher/count")
    public int getTeacherCount() {
        return teacherService.getTeacherCount();
    }

    @GetMapping("/api/teacher/countColleges")
    public int getCollegeCount() {
        return teacherService.getCollegeCount();
    }

    // 新增接口：获取所有教师信息
    @GetMapping("/api/teacher/all")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    // 新增接口：修改教师信息
    @PutMapping("/api/teacher/update")
    public int updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacher);
    }

    // 新增接口：删除教师信息
    @DeleteMapping("/api/teacher/delete/{id}")
    public int deleteTeacher(@PathVariable Long id) {
        return teacherService.deleteTeacher(id);
    }
}