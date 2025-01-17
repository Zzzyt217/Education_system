package org.example.work1.controller;

import org.example.work1.entity.Student;
import org.example.work1.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/students")
public class StudentListController {
    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/list")
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteStudent(@PathVariable("id") Long id) {
        int result = studentMapper.deleteStudent(id);
        return result > 0 ? "success" : "fail";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addStudent(@ModelAttribute Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setStatus(1); // 默认状态为在读
        student.setRoleId(2); // 默认角色ID为2（学生）
        int result = studentMapper.addStudent(student);
        return result > 0 ? "success" : "fail";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Student> searchStudents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) Integer status) {
        return studentMapper.searchStudents(keyword, className, status);
    }
}